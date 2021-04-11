package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.combine.Combiner
import dev.vgerasimov.shapelse.empty.Emptible

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
    * In case if this is some composite schema, for example [[ProductSchema]], recursively maps all it's childs.
    *
    * @param mapper the function to be applied on the meta
    * @tparam MR the type of resulted meta
    */
  def map[MR](mapper: M => MR): Schema[MR]

  private[shapelse] def empty[M1 : Emptible]: Schema[M1]
  private[shapelse] def mapMetaOnly[MR](mapper: M => M): Schema[M] = map(mapper)
  private[shapelse] def combine[M1, MR](other: Schema[M1])(implicit combiner: Combiner[M, M1, MR]): Schema[MR] =
    map(combiner(_, other.meta))
}

object Schema {

  private[shapelse] def combine[M1, M2, MR](left: Schema[M1], right: Schema[M2])(
    implicit
    combiner: Combiner[M1, M2, MR],
    e1: Emptible[M1],
    e2: Emptible[M2]
  ): Schema[MR] = (left, right) match {
    case (l: BooleanSchema[M1], r: BooleanSchema[M2])     => l.combine(r)
    case (l: CharSchema[M1], r: CharSchema[M2])           => l.combine(r)
    case (l: StringSchema[M1], r: StringSchema[M2])       => l.combine(r)
    case (l: ByteSchema[M1], r: ByteSchema[M2])           => l.combine(r)
    case (l: ShortSchema[M1], r: ShortSchema[M2])         => l.combine(r)
    case (l: IntSchema[M1], r: IntSchema[M2])             => l.combine(r)
    case (l: LongSchema[M1], r: LongSchema[M2])           => l.combine(r)
    case (l: FloatSchema[M1], r: FloatSchema[M2])         => l.combine(r)
    case (l: DoubleSchema[M1], r: DoubleSchema[M2])       => l.combine(r)
    case (l: OptionSchema[M1], r: OptionSchema[M2])       => l.combine(r)
    case (l: ListSchema[M1], r: ListSchema[M2])           => l.combine(r)
    case (l: ProductSchema[M1], r: ProductSchema[M2])     => l.combine(r)
    case (l: CoproductSchema[M1], r: CoproductSchema[M2]) => l.combine(r)
    case (l, r) =>
      sys.error(s"Invalid schema combining: left=${l.getClass} right=${r.getClass}")
  }

  private[shapelse] def combineChilds[M1, M2, MR](
    thisChilds: List[Schema[M1]],
    otherChilds: List[Schema[M2]]
  )(
    implicit
    combiner: Combiner[M1, M2, MR],
    e1: Emptible[M1],
    e2: Emptible[M2]
  ): List[Schema[MR]] =
    thisChilds
      .zipAll(otherChilds, e1, e2)
      .map({
        case (l: Schema[_], r: Schema[_])   => combine(l.asInstanceOf[Schema[M1]], r.asInstanceOf[Schema[M2]])
        case (l: Schema[_], _: Emptible[_]) => combine(l.asInstanceOf[Schema[M1]], l.empty(e2))
        case (_: Emptible[_], r: Schema[_]) => combine(r.empty(e1), r.asInstanceOf[Schema[M2]])
      })
}

//region Primitive schemas

sealed trait PrimitiveSchema[M] extends Schema[M]

final case class BooleanSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): BooleanSchema[M1] = BooleanSchema(e.empty)
  override def map[MR](mapper: M => MR): BooleanSchema[MR] = BooleanSchema(mapper(meta))
}

final case class CharSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): CharSchema[M1] = CharSchema(e.empty)
  override def map[MR](mapper: M => MR): CharSchema[MR] = CharSchema(mapper(meta))
}

final case class StringSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): StringSchema[M1] = StringSchema(e.empty)
  override def map[MR](mapper: M => MR): StringSchema[MR] = StringSchema(mapper(meta))
}

final case class ByteSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ByteSchema[M1] = ByteSchema(e.empty)
  override def map[MR](mapper: M => MR): ByteSchema[MR] = ByteSchema(mapper(meta))
}

final case class ShortSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ShortSchema[M1] = ShortSchema(e.empty)
  override def map[MR](mapper: M => MR): ShortSchema[MR] = ShortSchema(mapper(meta))
}

final case class IntSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): IntSchema[M1] = IntSchema(e.empty)
  override def map[MR](mapper: M => MR): IntSchema[MR] = IntSchema(mapper(meta))
}

final case class LongSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): LongSchema[M1] = LongSchema(e.empty)
  override def map[MR](mapper: M => MR): LongSchema[MR] = LongSchema(mapper(meta))
}

final case class FloatSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): FloatSchema[M1] = FloatSchema(e.empty)
  override def map[MR](mapper: M => MR): FloatSchema[MR] = FloatSchema(mapper(meta))
}

final case class DoubleSchema[M](override val meta: M) extends PrimitiveSchema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): DoubleSchema[M1] = DoubleSchema(e.empty)
  override def map[MR](mapper: M => MR): DoubleSchema[MR] = DoubleSchema(mapper(meta))
}

//endregion

//region Composite schemas

final case class OptionSchema[M](override val meta: M, schema: Schema[M]) extends Schema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): OptionSchema[M1] =
    OptionSchema(e.empty, schema.empty)
  override def map[MR](mapper: M => MR): OptionSchema[MR] = OptionSchema(mapper(meta), schema.map(mapper))
  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): OptionSchema[M] = OptionSchema(mapper(meta), schema)
  private[shapelse] def combine[M1, MR](
    other: OptionSchema[M1]
  )(implicit combiner: Combiner[M, M1, MR]): OptionSchema[MR] =
    map(m => combiner(m, other.meta))
}

final case class ListSchema[M](override val meta: M, schema: Schema[M]) extends Schema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ListSchema[M1] = ListSchema(e.empty, schema.empty)
  override def map[MR](mapper: M => MR): ListSchema[MR] = ListSchema(mapper(meta), schema.map(mapper))
  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): ListSchema[M] = ListSchema(mapper(meta), schema)
  private[shapelse] def combine[M1, MR](other: ListSchema[M1])(implicit combiner: Combiner[M, M1, MR]): ListSchema[MR] =
    map(m => combiner(m, other.meta))
}

final case class ProductSchema[M](override val meta: M, childs: List[Schema[M]]) extends Schema[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ProductSchema[M1] =
    ProductSchema(e.empty, childs.map(_.empty))
  override def map[MR](mapper: M => MR): ProductSchema[MR] = ProductSchema(mapper(meta), childs.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): ProductSchema[M] = ProductSchema(mapper(meta), childs)

  private[shapelse] def combine[M1, MR](other: ProductSchema[M1])(
    implicit
    combiner: Combiner[M, M1, MR],
    e1: Emptible[M],
    e2: Emptible[M1]
  ): ProductSchema[MR] =
    ProductSchema(
      combiner(this.meta, other.meta),
      Schema.combineChilds(this.childs, other.childs)
    )
}

final case class CoproductSchema[M](override val meta: M, childs: List[Schema[M]]) extends Schema[M] {

  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): CoproductSchema[M1] =
    CoproductSchema(e.empty, childs.map(_.empty))

  override def map[MR](mapper: M => MR): CoproductSchema[MR] = CoproductSchema(mapper(meta), childs.map(_.map(mapper)))

  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): CoproductSchema[M] =
    CoproductSchema(mapper(meta), childs)

  private[shapelse] def combine[M1, MR](other: CoproductSchema[M1])(
    implicit
    combiner: Combiner[M, M1, MR],
    e1: Emptible[M],
    e2: Emptible[M1]
  ): CoproductSchema[MR] =
    CoproductSchema(
      combiner(this.meta, other.meta),
      Schema.combineChilds(this.childs, other.childs)
    )
}

//endregion
