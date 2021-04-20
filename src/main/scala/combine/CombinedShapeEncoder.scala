package dev.vgerasimov.shapelse
package combine

private[shapelse] class CombinedShapeInstanceEncoder[M1 : MetaProvider, M2 : MetaProvider, MR, A] private (
  left: ShapeInstanceEncoder[M1, A],
  right: ShapeInstanceEncoder[M2, A]
)(
  implicit
  combiner: Combiner[M1, M2, MR]
) extends ShapeInstanceEncoder[MR, A] {
  override def encode(a: A): Shape[MR] =
    left
      .encode(a)
      .combine(
        right.encode(a),
        ifThisChildEmpty = Some(implicitly[MetaProvider[M1]].meta),
        ifThatChildEmpty = Some(implicitly[MetaProvider[M2]].meta)
      ) match {
      case Left(error) =>
        sys.error(s"Unexpected error while encoders combining: $error")
      case Right(shape) => shape
    }
}

private[shapelse] object CombinedShapeInstanceEncoder {

  def instance[M1 : MetaProvider, M2 : MetaProvider, MR, A](
    left: ShapeInstanceEncoder[M1, A],
    right: ShapeInstanceEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedShapeInstanceEncoder[M1, M2, MR, A] = new CombinedShapeInstanceEncoder(left, right)
}

private[shapelse] class CombinedShapeEncoder[M1 : MetaProvider, M2 : MetaProvider, MR, A] private (
  left: ShapeEncoder[M1, A],
  right: ShapeEncoder[M2, A]
)(
  implicit
  combiner: Combiner[M1, M2, MR]
) extends ShapeEncoder[MR, A] {
  override def encode: Shape[MR] =
    left.encode.combine(
      right.encode,
      ifThisChildEmpty = Some(implicitly[MetaProvider[M1]].meta),
      ifThatChildEmpty = Some(implicitly[MetaProvider[M2]].meta)
    ) match {
      case Left(error)  => sys.error(s"Unexpected error while encoders combining: $error")
      case Right(shape) => shape
    }
}

private[shapelse] object CombinedShapeEncoder {

  def instance[M1 : MetaProvider, M2 : MetaProvider, MR, A](
    left: ShapeEncoder[M1, A],
    right: ShapeEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedShapeEncoder[M1, M2, MR, A] = new CombinedShapeEncoder(left, right)
}
