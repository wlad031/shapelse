package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.combine.Combiner

// TODO: improve this doc
/** Represents some ADT's shape whether it's a sealed trait's subtype or case class' shapes.
  * @tparam A the type of stored meta
  */
sealed trait Shape[A] extends MetaProvider[A] {

  /** Returns new shape with the result of applying given function on shape's meta.
    *
    * In case if `this` is [[CompositeShape]] function will be applied on it's meta recursively.
    *
    * @param mapper the function to be applied on meta
    * @tparam B the type of resulted meta
    */
  def map[B](mapper: A => B): Shape[B]

  /** Returns new shape with the result of applying given function in shape's meta.
    *
    * In case if `this` is [[CompositeShape]] meta of the childs won't be affected.
    *
    * @param mapper the function to be applied on the meta
    */
  private[shapelse] def mapMetaOnly(mapper: A => A): Shape[A]

  /** Returns new shape with the result of applying given function of `this`' meta and given one.
    *
    * In case of `this` and given one are actually different shapes, returns [[CombiningError]].
    *
    * @param that the shape `this` to be combined with
    * @param combiner the function to be applied on meta
    * @tparam B the type of meta of the given shape
    * @tparam C the type of meta of resulted shape
    */
  def combine[B, C](that: Shape[B])(
    implicit combiner: Combiner[A, B, C]
  ): Either[CombiningError, Shape[C]]

  private[shapelse] def combine[B, C](
    that: Shape[B],
    ifThisChildEmpty: => Option[A],
    ifThatChildEmpty: => Option[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]]
}

//region Primitive shapes

sealed trait PrimitiveShape[A] extends Shape[A] {
  override private[shapelse] def combine[B, C](
    that: Shape[B],
    ifThisChildEmpty: => Option[A],
    ifThatChildEmpty: => Option[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = combine(that)
}

final case class BooleanShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): BooleanShape[B] = BooleanShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): BooleanShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case BooleanShape(rightMeta) => Right(BooleanShape(combiner(meta, rightMeta)))
    case that                    => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class CharShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): CharShape[B] = CharShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): CharShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case CharShape(rightMeta) => Right(CharShape(combiner(meta, rightMeta)))
    case that                 => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class StringShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): StringShape[B] = StringShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): StringShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case StringShape(rightMeta) => Right(StringShape(combiner(meta, rightMeta)))
    case that                   => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class ByteShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): ByteShape[B] = ByteShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): ByteShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case ByteShape(rightMeta) => Right(ByteShape(combiner(meta, rightMeta)))
    case that                 => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class ShortShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): ShortShape[B] = ShortShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): ShortShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case ShortShape(rightMeta) => Right(ShortShape(combiner(meta, rightMeta)))
    case that                  => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class IntShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): IntShape[B] = IntShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): IntShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case IntShape(rightMeta) => Right(IntShape(combiner(meta, rightMeta)))
    case that                => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class LongShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): LongShape[B] = LongShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): LongShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case LongShape(rightMeta) => Right(LongShape(combiner(meta, rightMeta)))
    case that                 => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class FloatShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): FloatShape[B] = FloatShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): FloatShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case FloatShape(rightMeta) => Right(FloatShape(combiner(meta, rightMeta)))
    case that                  => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class DoubleShape[A](override val meta: A) extends PrimitiveShape[A] {
  override def map[B](mapper: A => B): DoubleShape[B] = DoubleShape(mapper(meta))
  override private[shapelse] def mapMetaOnly(mapper: A => A): DoubleShape[A] = map(mapper)

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] = that match {
    case DoubleShape(rightMeta) => Right(DoubleShape(combiner(meta, rightMeta)))
    case that                   => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

//endregion

//region Composite shapes

sealed trait CompositeShape[A] extends Shape[A] {

  override def combine[B, C](
    that: Shape[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, Shape[C]] =
    combine(that, None, None)

  protected def liftCombiningErrors[B, C](
    combiningResult: List[Either[CombiningError, Shape[C]]]
  ): Either[CombiningError, List[Shape[C]]] = combiningResult.partition(_.isLeft) match {
    case (Nil, rightShapes) => Right(for (Right(shape) <- rightShapes) yield shape)
    case (leftErrors, _) =>
      val errors = for (Left(error) <- leftErrors) yield error
      Left(errors match {
        case x :: Nil => x
        case xs       => CombiningError.Multiple(xs)
      })
  }
}

final case class ListShape[A](override val meta: A, childs: List[Shape[A]]) extends CompositeShape[A] {

  override def map[B](mapper: A => B): ListShape[B] = ListShape(mapper(meta), childs.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly(mapper: A => A): ListShape[A] = ListShape(mapper(meta), childs)

  override def combine[B, C](
    that: Shape[B],
    ifThisChildEmpty: => Option[A],
    ifThatChildEmpty: => Option[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, ListShape[C]] = that match {
    case ListShape(thatMeta, thatChilds) =>
      liftCombiningErrors(
        this.childs
          .map(Some(_))
          .zipAll(
            thatChilds.map(Some(_)),
            this.childs.headOption.orElse(for {
              e        <- ifThisChildEmpty
              thatHead <- thatChilds.headOption
            } yield thatHead.map(_ => e)),
            thatChilds.headOption.orElse(for {
              e        <- ifThatChildEmpty
              thisHead <- this.childs.headOption
            } yield thisHead.map(_ => e))
          )
          .map({
            case (leftShape: Some[Shape[_]], rightShape: Some[Shape[_]]) =>
              leftShape.get
                .asInstanceOf[Shape[A]]
                .combine(rightShape.get.asInstanceOf[Shape[B]], ifThisChildEmpty, ifThatChildEmpty)
            case (leftShape: Some[Shape[_]], _) => Left(CombiningError.IfEmptyNotProvided(leftShape.get))
            case (_, rightShape)                => Left(CombiningError.IfEmptyNotProvided(rightShape.get))
          })
      ).map(combinedChilds => ListShape(combiner(this.meta, thatMeta), combinedChilds))
    case that => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class ProductShape[A](override val meta: A, childs: List[Shape[A]]) extends CompositeShape[A] {

  override def map[B](mapper: A => B): ProductShape[B] = ProductShape(mapper(meta), childs.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly(mapper: A => A): ProductShape[A] = ProductShape(mapper(meta), childs)

  override private[shapelse] def combine[B, C](
    that: Shape[B],
    ifThisChildEmpty: => Option[A],
    ifThatChildEmpty: => Option[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, ProductShape[C]] = that match {
    case ProductShape(thatMeta, thatChilds) =>
      liftCombiningErrors(
        this.childs
          .zipAll(thatChilds, ifThisChildEmpty, ifThatChildEmpty)
          .map({
            case (_: Shape[_], None) => Left(CombiningError.IfEmptyNotProvided(that))
            case (None, _: Shape[_]) => Left(CombiningError.IfEmptyNotProvided(this))
            case (leftShape: Shape[_], rightShape: Shape[_]) =>
              leftShape
                .asInstanceOf[Shape[A]]
                .combine(rightShape.asInstanceOf[Shape[B]], ifThisChildEmpty, ifThatChildEmpty)(combiner)
            case (leftShape: Shape[_], Some(ifEmpty)) =>
              leftShape.asInstanceOf[Shape[A]].combine(leftShape.map(_ => ifEmpty.asInstanceOf[B]))
            case (Some(ifEmpty), rightShape: Shape[_]) =>
              rightShape.map(_ => ifEmpty.asInstanceOf[A]).combine(rightShape.asInstanceOf[Shape[B]])
          })
      ).map(combinedChilds => ProductShape(combiner(this.meta, thatMeta), combinedChilds))
    case that => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

final case class CoproductShape[A](override val meta: A, childs: List[Shape[A]]) extends CompositeShape[A] {

  override def map[B](mapper: A => B): CoproductShape[B] = CoproductShape(mapper(meta), childs.map(_.map(mapper)))
  override private[shapelse] def mapMetaOnly(mapper: A => A): CoproductShape[A] = CoproductShape(mapper(meta), childs)

  override def combine[B, C](
    that: Shape[B],
    ifThisChildEmpty: => Option[A],
    ifThatChildEmpty: => Option[B]
  )(implicit combiner: Combiner[A, B, C]): Either[CombiningError, CoproductShape[C]] = that match {
    case CoproductShape(thatMeta, thatChilds) =>
      liftCombiningErrors(
        this.childs
          .zipAll(thatChilds, ifThisChildEmpty, ifThatChildEmpty)
          .map({
            case (_: Shape[_], None) => Left(CombiningError.IfEmptyNotProvided(that))
            case (None, _: Shape[_]) => Left(CombiningError.IfEmptyNotProvided(this))
            case (leftShape: Shape[_], rightShape: Shape[_]) =>
              leftShape
                .asInstanceOf[Shape[A]]
                .combine(rightShape.asInstanceOf[Shape[B]], ifThisChildEmpty, ifThatChildEmpty)(combiner)
            case (leftShape: Shape[_], Some(ifEmpty)) =>
              leftShape.asInstanceOf[Shape[A]].combine(leftShape.map(_ => ifEmpty.asInstanceOf[B]))
            case (Some(ifEmpty), rightShape: Shape[_]) =>
              rightShape.map(_ => ifEmpty.asInstanceOf[A]).combine(rightShape.asInstanceOf[Shape[B]])
          })
      ).map(combinedChilds => CoproductShape(combiner(this.meta, thatMeta), combinedChilds))
    case that => Left(CombiningError.IncompatibleShapes(this, that))
  }
}

//endregion
