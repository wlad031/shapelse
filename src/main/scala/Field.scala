package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.empty.Empty

import scala.collection.immutable.ListMap

/** Represents some ADT's field whether it's a sealed trait's subtype or case class' fields.
  *
  * Basically, it's a typed container for some information (meta) about such fields.
  *
  * @tparam M the type of stored meta
  */
sealed trait Field[M] {

  /** Returns field's metadata. */
  def meta: M

  /** Returns new field with the result of applying given function on field's meta.
    *
    * In case if this is some complex field, for example [[ProductField]], recursively maps all it's childs.
    *
    * @param mapper the function to be applied on the meta
    * @tparam MR the type of resulted meta
    *
    * @example {{{
    * val field = BooleanField[Option[Int]](Some(1))
    * val mapped = field.map(_.map(x => x + 10))
    * // mapped: BooleanField[Option[Int]] = BooleanField(Some(11))
    * }}}
    *
    * @example {{{
    * val field = ProductField[Option[Int]](
    *   Some(1),
    *   Map(
    *     Symbol("a") -> IntField[Option[Int]](Some(10)),
    *     Symbol("b") -> StringField[Option[Int]](Some(11)),
    *     Symbol("c") -> BooleanField[Option[Int]](None),
    *   ))
    * val mapped = field.map(_.map(x => x + 10))
    * // mapped: ProductField[Option[Int]] = ProductField(
    * //   Some(11),
    * //   Map(
    * //     Symbol("a") -> IntField(Some(20)),
    * //     Symbol("b") -> StringField(Some(21)),
    * //     Symbol("c") -> BooleanField(None),
    * // ))
    * }}}
    */
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

  /** Combines meta of this field and other given field using provided combiner function.
    *
    * @param combiner the function of two arguments to be applied on this' and other's meta
    * @param other the field to be combined with this one
    * @tparam M1 the type of meta of other field
    * @tparam MR the type of resulted meta
    *
    * @example {{{
    * val field1 = ProductField[Option[Int]](
    *   Some(1),
    *   Map(
    *     Symbol("a") -> IntField[Option[Int]](Some(10)),
    *     Symbol("b") -> StringField[Option[Int]](Some(11)),
    *   ))
    * val field2 = ProductField[Option[String]](
    *   Some("one"),
    *   Map(
    *     Symbol("a") -> IntField[Option[String]](Some("ten")),
    *     Symbol("b") -> StringField[Option[String]](Some("eleven")),
    *   ))
    * val makeTuple = (o1: Option[Int], o2: Option[String]) => (o1, o2)
    * val combined = field1.combine(makeTuple)(field2)
    * // combined: ProductField[(Option[Int], Option[String])] = ProductField(
    * //  (Some(1), Some("one")),
    * //  Map(
    * //    Symbol("a") -> IntField((Some(10), Some("ten")),
    * //    Symbol("b") -> StringField((Some(11), Some("eleven")),
    * //  ))
    * }}}
    */
  def combine[M1, MR](combiner: (M, M1) => MR)(other: Field[M1]): Field[MR] = {
    def combineChilds(thisChilds: Map[Symbol, Field[M]], otherChilds: Map[Symbol, Field[M1]]): Map[Symbol, Field[MR]] =
      thisChilds.map({ case (symbol, lField) => (symbol, lField.combine(combiner)(otherChilds(symbol))) })

    val mapper: M => MR = m => combiner(m, other.meta)
    this match {
      // TODO: is there a way to avoid casting?
      case ProductField(meta, childs) =>
        ProductField(mapper(meta), ListMap.from(combineChilds(childs, other.asInstanceOf[ProductField[M1]].childs)))
      case CoproductField(meta, childs) =>
        CoproductField(mapper(meta), combineChilds(childs, other.asInstanceOf[CoproductField[M1]].childs))
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
