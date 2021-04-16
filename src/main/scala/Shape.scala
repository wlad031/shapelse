package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.combine.Combiner
import dev.vgerasimov.shapelse.empty.Emptible

/** Represents some ADT's shape whether it's a sealed trait's subtype or case class' shapes.
  *
  * Basically, it's a typed container for some information (meta) about such shapes.
  *
  * @tparam M the type of stored meta
  */
sealed trait Shape[M] {

  /** Returns shape's metadata. */
  def meta: M

  /** Returns new shape with the result of applying given function on shape's meta.
    *
    * In case if this is some composite shape, for example [[ProductShape]], recursively maps all it's childs.
    *
    * @param mapper the function to be applied on the meta
    * @tparam MR the type of resulted meta
    */
  def map[MR](mapper: M => MR): Shape[MR]

  private[shapelse] def empty[M1 : Emptible]: Shape[M1]
  private[shapelse] def mapMetaOnly[MR](mapper: M => M): Shape[M] = map(mapper)
  private[shapelse] def combine[M1, MR](other: Shape[M1])(implicit combiner: Combiner[M, M1, MR]): Shape[MR] =
    map(combiner(_, other.meta))
}

object Shape {

  private[shapelse] def combine[M1, M2, MR](left: Shape[M1], right: Shape[M2])(
    implicit
    combiner: Combiner[M1, M2, MR],
    e1: Emptible[M1],
    e2: Emptible[M2]
  ): Shape[MR] = (left, right) match {
    case (l: BooleanShape[M1], r: BooleanShape[M2])     => l.combine(r)
    case (l: CharShape[M1], r: CharShape[M2])           => l.combine(r)
    case (l: StringShape[M1], r: StringShape[M2])       => l.combine(r)
    case (l: ByteShape[M1], r: ByteShape[M2])           => l.combine(r)
    case (l: ShortShape[M1], r: ShortShape[M2])         => l.combine(r)
    case (l: IntShape[M1], r: IntShape[M2])             => l.combine(r)
    case (l: LongShape[M1], r: LongShape[M2])           => l.combine(r)
    case (l: FloatShape[M1], r: FloatShape[M2])         => l.combine(r)
    case (l: DoubleShape[M1], r: DoubleShape[M2])       => l.combine(r)
    case (l: OptionShape[M1], r: OptionShape[M2])       => l.combine(r)
    case (l: ListShape[M1], r: ListShape[M2])           => l.combine(r)
    case (l: ProductShape[M1], r: ProductShape[M2])     => l.combine(r)
    case (l: CoproductShape[M1], r: CoproductShape[M2]) => l.combine(r)
    case (l, r) =>
      sys.error(s"Invalid shape combining: left=${l.getClass} right=${r.getClass}")
  }

  private[shapelse] def combineChilds[M1, M2, MR](
    thisChilds: List[Shape[M1]],
    otherChilds: List[Shape[M2]]
  )(
    implicit
    combiner: Combiner[M1, M2, MR],
    e1: Emptible[M1],
    e2: Emptible[M2]
  ): List[Shape[MR]] =
    thisChilds
      .zipAll(otherChilds, e1, e2)
      .map({
        case (l: Shape[_], r: Shape[_])   => combine(l.asInstanceOf[Shape[M1]], r.asInstanceOf[Shape[M2]])
        case (l: Shape[_], _: Emptible[_]) => combine(l.asInstanceOf[Shape[M1]], l.empty(e2))
        case (_: Emptible[_], r: Shape[_]) => combine(r.empty(e1), r.asInstanceOf[Shape[M2]])
      })
}

//region Primitive shapes

sealed trait PrimitiveShape[M] extends Shape[M]

final case class BooleanShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): BooleanShape[M1] = BooleanShape(e.empty)
  override def map[MR](mapper: M => MR): BooleanShape[MR] = BooleanShape(mapper(meta))
}

final case class CharShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): CharShape[M1] = CharShape(e.empty)
  override def map[MR](mapper: M => MR): CharShape[MR] = CharShape(mapper(meta))
}

final case class StringShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): StringShape[M1] = StringShape(e.empty)
  override def map[MR](mapper: M => MR): StringShape[MR] = StringShape(mapper(meta))
}

final case class ByteShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ByteShape[M1] = ByteShape(e.empty)
  override def map[MR](mapper: M => MR): ByteShape[MR] = ByteShape(mapper(meta))
}

final case class ShortShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ShortShape[M1] = ShortShape(e.empty)
  override def map[MR](mapper: M => MR): ShortShape[MR] = ShortShape(mapper(meta))
}

final case class IntShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): IntShape[M1] = IntShape(e.empty)
  override def map[MR](mapper: M => MR): IntShape[MR] = IntShape(mapper(meta))
}

final case class LongShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): LongShape[M1] = LongShape(e.empty)
  override def map[MR](mapper: M => MR): LongShape[MR] = LongShape(mapper(meta))
}

final case class FloatShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): FloatShape[M1] = FloatShape(e.empty)
  override def map[MR](mapper: M => MR): FloatShape[MR] = FloatShape(mapper(meta))
}

final case class DoubleShape[M](override val meta: M) extends PrimitiveShape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): DoubleShape[M1] = DoubleShape(e.empty)
  override def map[MR](mapper: M => MR): DoubleShape[MR] = DoubleShape(mapper(meta))
}

//endregion

//region Composite shapes

final case class OptionShape[M](override val meta: M, child: Option[Shape[M]]) extends Shape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): OptionShape[M1] =
    OptionShape(e.empty, child.map(_.empty))
  override def map[MR](mapper: M => MR): OptionShape[MR] = OptionShape(mapper(meta), child.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): OptionShape[M] = OptionShape(mapper(meta), child)
  private[shapelse] def combine[M1, MR](
    other: OptionShape[M1]
  )(implicit combiner: Combiner[M, M1, MR]): OptionShape[MR] = map(m => combiner(m, other.meta))
}

final case class ListShape[M](override val meta: M, childs: List[Shape[M]]) extends Shape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ListShape[M1] =
    ListShape(e.empty, childs.map(_.empty))
  override def map[MR](mapper: M => MR): ListShape[MR] = ListShape(mapper(meta), childs.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): ListShape[M] = ListShape(mapper(meta), childs)
  private[shapelse] def combine[M1, MR](other: ListShape[M1])(
    implicit
    combiner: Combiner[M, M1, MR]
  ): ListShape[MR] = ListShape(
    combiner(meta, other.meta),
    Shape.combineChilds(childs, other.childs)(combiner, Emptible(childs.head.meta), Emptible(other.childs.head.meta))
  )
}

final case class ProductShape[M](override val meta: M, childs: List[Shape[M]]) extends Shape[M] {
  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): ProductShape[M1] =
    ProductShape(e.empty, childs.map(_.empty))
  override def map[MR](mapper: M => MR): ProductShape[MR] = ProductShape(mapper(meta), childs.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): ProductShape[M] = ProductShape(mapper(meta), childs)

  private[shapelse] def combine[M1, MR](other: ProductShape[M1])(
    implicit
    combiner: Combiner[M, M1, MR],
    e1: Emptible[M],
    e2: Emptible[M1]
  ): ProductShape[MR] = ProductShape(
    combiner(this.meta, other.meta),
    Shape.combineChilds(this.childs, other.childs)
  )
}

final case class CoproductShape[M](override val meta: M, childs: List[Shape[M]]) extends Shape[M] {

  override private[shapelse] def empty[M1](implicit e: Emptible[M1]): CoproductShape[M1] =
    CoproductShape(e.empty, childs.map(_.empty))

  override def map[MR](mapper: M => MR): CoproductShape[MR] = CoproductShape(mapper(meta), childs.map(_.map(mapper)))

  override private[shapelse] def mapMetaOnly[MR](mapper: M => M): CoproductShape[M] =
    CoproductShape(mapper(meta), childs)

  private[shapelse] def combine[M1, MR](other: CoproductShape[M1])(
    implicit
    combiner: Combiner[M, M1, MR],
    e1: Emptible[M],
    e2: Emptible[M1]
  ): CoproductShape[MR] = CoproductShape(
    combiner(this.meta, other.meta),
    Shape.combineChilds(this.childs, other.childs)
  )
}

//endregion
