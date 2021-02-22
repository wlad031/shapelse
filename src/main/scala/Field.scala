package dev.vgerasimov.shapelse

import scala.collection.immutable.ListMap

sealed trait Field[M] {
  def meta: M

  def map[MR](mapper: M => MR): Field[MR] = this match {
    case BooleanField(meta)       => BooleanField(mapper(meta))
    case CharField(meta)          => CharField(mapper(meta))
    case StringField(meta)        => StringField(mapper(meta))
    case ShortField(meta)         => ShortField(mapper(meta))
    case IntField(meta)           => IntField(mapper(meta))
    case LongField(meta)          => LongField(mapper(meta))
    case FloatField(meta)         => FloatField(mapper(meta))
    case DoubleField(meta)        => DoubleField(mapper(meta))
    case OptionField(meta, field) => OptionField(mapper(meta), field.map(mapper))
    case ListField(meta, field)   => ListField(mapper(meta), field.map(mapper))
    case ProductField(meta, childs) =>
      ProductField(mapper(meta), ListMap.from(childs.view.mapValues(_.map(mapper)).toMap))
    case CoproductField(meta, childs) => CoproductField(mapper(meta), childs.view.mapValues(_.map(mapper)).toMap)
  }

  def bimap[M2, MR](bimapper: (M, M2) => MR)(other: Field[M2]): Field[MR] = {
    val mapper: M => MR = m => bimapper(m, other.meta)
    this match {
      case ProductField(meta, childs) =>
        ProductField(
          bimapper(meta, other.meta),
          ListMap.from(
            (childs zip other.asInstanceOf[ProductField[M2]].childs)
              .map({ case ((lSym, lField), (_, rField)) => (lSym, lField.bimap(bimapper)(rField)) })
              .toMap
          )
        )
      case CoproductField(meta, childs) =>
        CoproductField(
          bimapper(meta, other.meta),
          (childs zip other.asInstanceOf[CoproductField[M2]].childs)
            .map({ case ((lSym, lField), (_, rField)) => (lSym, lField.bimap(bimapper)(rField)) })
            .toMap
        )
      case _ => this.map(mapper)
    }
  }
}

sealed trait PrimitiveField[M] extends Field[M]

case class BooleanField[M](override val meta: M) extends PrimitiveField[M]
object BooleanField {
  def empty: BooleanField[Empty] = BooleanField(Empty())
}

case class CharField[M](override val meta: M) extends PrimitiveField[M]
object CharField {
  def empty: CharField[Empty] = CharField(Empty())
}

case class StringField[M](override val meta: M) extends PrimitiveField[M]
object StringField {
  def empty: StringField[Empty] = StringField(Empty())
}

case class ShortField[M](override val meta: M) extends PrimitiveField[M]
object ShortField {
  def empty: ShortField[Empty] = ShortField(Empty())
}

case class IntField[M](override val meta: M) extends PrimitiveField[M]
object IntField {
  def empty: IntField[Empty] = IntField(Empty())
}

case class LongField[M](override val meta: M) extends PrimitiveField[M]
object LongField {
  def empty: LongField[Empty] = LongField(Empty())
}

case class FloatField[M](override val meta: M) extends PrimitiveField[M]
object FloatField {
  def empty: FloatField[Empty] = FloatField(Empty())
}

case class DoubleField[M](override val meta: M) extends PrimitiveField[M]
object DoubleField {
  def empty: DoubleField[Empty] = DoubleField(Empty())
}

final case class OptionField[M](override val meta: M, field: Field[M]) extends Field[M]
final case class ListField[M](override val meta: M, field: Field[M]) extends Field[M]

final case class ProductField[M](override val meta: M, childs: ListMap[Symbol, Field[M]]) extends Field[M]
object ProductField {
  def empty(childs: ListMap[Symbol, Field[Empty]]): ProductField[Empty] = ProductField(Empty(), childs)
}

final case class CoproductField[M](override val meta: M, childs: Map[Symbol, Field[M]]) extends Field[M]
object CoproductField {
  def empty(childs: Map[Symbol, Field[Empty]]): CoproductField[Empty] = CoproductField(Empty(), childs)
}
