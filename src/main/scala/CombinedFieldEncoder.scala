package dev.vgerasimov.shapelse

import scala.collection.immutable.ListMap

trait CombinedFieldEncoder[M, A] extends FieldEncoder[M, A] {}

object CombinedFieldEncoder {

  def instance[M1, M2, MR, A](left: FieldEncoder[M1, A], right: FieldEncoder[M2, A])(
    implicit metaCombiner: Combiner[M1, M2, MR]
  ): CombinedFieldEncoder[MR, A] =
    new CombinedFieldEncoder[MR, A] {
      private def unexpected: Nothing = sys.error("Illegal state: combining field encoders")

      override def encode: Field[MR] = f(left.encode, right.encode)

      // TODO: move traversing to the Field class itself
      private def f(leftField: Field[M1], rightField: Field[M2]): Field[MR] = {
        leftField match {
          case field: PrimitiveField[M1] =>
            field match {
              case BooleanField(meta) => BooleanField(metaCombiner(meta, rightField.meta))
              case CharField(meta)    => CharField(metaCombiner(meta, rightField.meta))
              case StringField(meta)  => StringField(metaCombiner(meta, rightField.meta))
              case ShortField(meta)   => ShortField(metaCombiner(meta, rightField.meta))
              case IntField(meta)     => IntField(metaCombiner(meta, rightField.meta))
              case LongField(meta)    => LongField(metaCombiner(meta, rightField.meta))
              case FloatField(meta)   => FloatField(metaCombiner(meta, rightField.meta))
              case DoubleField(meta)  => DoubleField(metaCombiner(meta, rightField.meta))
            }
          case OptionField(meta, field) =>
            OptionField(metaCombiner(meta, rightField.meta), f(field, rightField match {
              case OptionField(meta, fieldR) => fieldR
              case _                         => unexpected
            }))
          case ListField(meta, field) =>
            ListField(metaCombiner(meta, rightField.meta), f(field, rightField match {
              case ListField(meta, fieldR) => fieldR
              case _                       => unexpected
            }))
          case ProductField(meta, childs) =>
            ProductField(
              metaCombiner(meta, rightField.meta),
              rightField match {
                case ProductField(meta, childsR) =>
                  ListMap.from {
                    (childs zip childsR)
                      .map({
                        case ((k1, v1), (k2, v2)) => (k1, f(v1, v2))
                      })
                      .toMap
                  }
                case _ => unexpected
              }
            )
          case CoproductField(meta, childs) =>
            CoproductField(
              metaCombiner(meta, rightField.meta),
              rightField match {
                case CoproductField(meta, childsR) =>
                  ListMap.from {
                    (childs zip childsR)
                      .map({
                        case ((k1, v1), (k2, v2)) => (k1, f(v1, v2))
                      })
                      .toMap
                  }
                case _ => unexpected
              }
            )
        }
      }
    }
}
