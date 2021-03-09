package dev.vgerasimov.shapelse

import empty.Empty

import scala.collection.immutable.ListMap

/** Represents some ADT's schema whether it's a sealed trait's subtype or case class' schemas.
  *
  * Basically, it's a typed container for some information (meta) about such schemas.
  *
  * @tparam M the type of stored meta
  */
sealed trait Schema[M] {

  /** Returns schema's metadata. */
  def meta: M

  /** Returns new schema with the result of applying given function on schema's meta.
    *
    * In case if this is some complex schema, for example [[ProductSchema]], recursively maps all it's childs.
    *
    * @param mapper the function to be applied on the meta
    * @tparam MR the type of resulted meta
    * @example {{{
    * val schema = BooleanSchema[Option[Int]](Some(1))
    * val mapped = schema.map(_.map(x => x + 10))
    * // mapped: BooleanSchema[Option[Int]] = BooleanSchema(Some(11))
    * }}}
    * @example {{{
    * val schema = ProductSchema[Option[Int]](
    *   Some(1),
    *   Map(
    *     Symbol("a") -> IntSchema[Option[Int]](Some(10)),
    *     Symbol("b") -> StringSchema[Option[Int]](Some(11)),
    *     Symbol("c") -> BooleanSchema[Option[Int]](None),
    *   ))
    * val mapped = schema.map(_.map(x => x + 10))
    * // mapped: ProductSchema[Option[Int]] = ProductSchema(
    * //   Some(11),
    * //   Map(
    * //     Symbol("a") -> IntSchema(Some(20)),
    * //     Symbol("b") -> StringSchema(Some(21)),
    * //     Symbol("c") -> BooleanSchema(None),
    * // ))
    * }}}
    */
  def map[MR](mapper: M => MR): Schema[MR] = this match {
    case BooleanSchema(meta)        => BooleanSchema(mapper(meta))
    case CharSchema(meta)           => CharSchema(mapper(meta))
    case StringSchema(meta)         => StringSchema(mapper(meta))
    case ShortSchema(meta)          => ShortSchema(mapper(meta))
    case IntSchema(meta)            => IntSchema(mapper(meta))
    case LongSchema(meta)           => LongSchema(mapper(meta))
    case FloatSchema(meta)          => FloatSchema(mapper(meta))
    case DoubleSchema(meta)         => DoubleSchema(mapper(meta))
    case OptionSchema(meta, schema) => OptionSchema(mapper(meta), schema.map(mapper))
    case ListSchema(meta, schema)   => ListSchema(mapper(meta), schema.map(mapper))
    case ProductSchema(meta, childs) =>
      ProductSchema(mapper(meta), ListMap.from(childs.view.mapValues(_.map(mapper)).toMap))
    case CoproductSchema(meta, childs) => CoproductSchema(mapper(meta), childs.view.mapValues(_.map(mapper)).toMap)
  }

  /** Combines meta of this schema and other given schema using provided combiner function.
    *
    * @param combiner the function of two arguments to be applied on this' and other's meta
    * @param other the schema to be combined with this one
    * @tparam M1 the type of meta of other schema
    * @tparam MR the type of resulted meta
    *
    * @example {{{
    * val schema1 = ProductSchema[Option[Int]](
    *   Some(1),
    *   Map(
    *     Symbol("a") -> IntSchema[Option[Int]](Some(10)),
    *     Symbol("b") -> StringSchema[Option[Int]](Some(11)),
    *   ))
    * val schema2 = ProductSchema[Option[String]](
    *   Some("one"),
    *   Map(
    *     Symbol("a") -> IntSchema[Option[String]](Some("ten")),
    *     Symbol("b") -> StringSchema[Option[String]](Some("eleven")),
    *   ))
    * val makeTuple = (o1: Option[Int], o2: Option[String]) => (o1, o2)
    * val combined = schema1.combine(makeTuple)(schema2)
    * // combined: ProductSchema[(Option[Int], Option[String])] = ProductSchema(
    * //  (Some(1), Some("one")),
    * //  Map(
    * //    Symbol("a") -> IntSchema((Some(10), Some("ten")),
    * //    Symbol("b") -> StringSchema((Some(11), Some("eleven")),
    * //  ))
    * }}}
    */
  def combine[M1, MR](combiner: (M, M1) => MR)(other: Schema[M1]): Schema[MR] = {
    def combineChilds(
      thisChilds: Map[Symbol, Schema[M]],
      otherChilds: Map[Symbol, Schema[M1]]
    ): Map[Symbol, Schema[MR]] =
      thisChilds.map({ case (symbol, lSchema) => (symbol, lSchema.combine(combiner)(otherChilds(symbol))) })

    val mapper: M => MR = m => combiner(m, other.meta)
    this match {
      // TODO: is there a way to avoid casting?
      case ProductSchema(meta, childs) =>
        ProductSchema(mapper(meta), ListMap.from(combineChilds(childs, other.asInstanceOf[ProductSchema[M1]].childs)))
      case CoproductSchema(meta, childs) =>
        CoproductSchema(mapper(meta), combineChilds(childs, other.asInstanceOf[CoproductSchema[M1]].childs))
      case _ => this.map(mapper)
    }
  }
}

sealed trait PrimitiveSchema[M] extends Schema[M]

case class BooleanSchema[M](override val meta: M) extends PrimitiveSchema[M]
object BooleanSchema {
  def empty: BooleanSchema[Empty] = BooleanSchema(Empty())
}

case class CharSchema[M](override val meta: M) extends PrimitiveSchema[M]
object CharSchema {
  def empty: CharSchema[Empty] = CharSchema(Empty())
}

case class StringSchema[M](override val meta: M) extends PrimitiveSchema[M]
object StringSchema {
  def empty: StringSchema[Empty] = StringSchema(Empty())
}

case class ShortSchema[M](override val meta: M) extends PrimitiveSchema[M]
object ShortSchema {
  def empty: ShortSchema[Empty] = ShortSchema(Empty())
}

case class IntSchema[M](override val meta: M) extends PrimitiveSchema[M]
object IntSchema {
  def empty: IntSchema[Empty] = IntSchema(Empty())
}

case class LongSchema[M](override val meta: M) extends PrimitiveSchema[M]
object LongSchema {
  def empty: LongSchema[Empty] = LongSchema(Empty())
}

case class FloatSchema[M](override val meta: M) extends PrimitiveSchema[M]
object FloatSchema {
  def empty: FloatSchema[Empty] = FloatSchema(Empty())
}

case class DoubleSchema[M](override val meta: M) extends PrimitiveSchema[M]
object DoubleSchema {
  def empty: DoubleSchema[Empty] = DoubleSchema(Empty())
}

final case class OptionSchema[M](override val meta: M, schema: Schema[M]) extends Schema[M]
final case class ListSchema[M](override val meta: M, schema: Schema[M]) extends Schema[M]

final case class ProductSchema[M](override val meta: M, childs: ListMap[Symbol, Schema[M]]) extends Schema[M]
object ProductSchema {
  def empty(childs: ListMap[Symbol, Schema[Empty]]): ProductSchema[Empty] = ProductSchema(Empty(), childs)
}

final case class CoproductSchema[M](override val meta: M, childs: Map[Symbol, Schema[M]]) extends Schema[M]
object CoproductSchema {
  def empty(childs: Map[Symbol, Schema[Empty]]): CoproductSchema[Empty] = CoproductSchema(Empty(), childs)
}
