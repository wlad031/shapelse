package dev.vgerasimov.shapelse
package combine

import dev.vgerasimov.shapelse.empty.Emptible

private[shapelse] class CombinedShapeInstanceEncoder[M1 : Emptible, M2 : Emptible, MR, A] private (
  left: ShapeInstanceEncoder[M1, A],
  right: ShapeInstanceEncoder[M2, A]
)(
  implicit
  combiner: Combiner[M1, M2, MR]
) extends ShapeInstanceEncoder[MR, A] {
  override def encode(a: A): Shape[MR] = Shape.combine(left.encode(a), right.encode(a))
}

private[shapelse] object CombinedShapeInstanceEncoder {

  def instance[M1 : Emptible, M2 : Emptible, MR, A](
    left: ShapeInstanceEncoder[M1, A],
    right: ShapeInstanceEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedShapeInstanceEncoder[M1, M2, MR, A] = new CombinedShapeInstanceEncoder(left, right)
}

private[shapelse] class CombinedShapeEncoder[M1 : Emptible, M2 : Emptible, MR, A] private (
  left: ShapeEncoder[M1, A],
  right: ShapeEncoder[M2, A]
)(
  implicit
  combiner: Combiner[M1, M2, MR]
) extends ShapeEncoder[MR, A] {
  override def encode: Shape[MR] = Shape.combine(left.encode, right.encode)
}

private[shapelse] object CombinedShapeEncoder {

  def instance[M1 : Emptible, M2 : Emptible, MR, A](
    left: ShapeEncoder[M1, A],
    right: ShapeEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedShapeEncoder[M1, M2, MR, A] = new CombinedShapeEncoder(left, right)
}
