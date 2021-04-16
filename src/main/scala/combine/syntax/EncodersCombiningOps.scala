package dev.vgerasimov.shapelse
package combine
package syntax

import dev.vgerasimov.shapelse.empty.Emptible

trait EncodersCombiningOps {

  implicit class ShapeInstanceEncoderOps[M : Emptible, A](self: ShapeInstanceEncoder[M, A]) {

    def combine[M1 : Emptible, MR](other: ShapeInstanceEncoder[M1, A])(
      implicit
      metaCombiner: Combiner[M, M1, MR]
    ): ShapeInstanceEncoder[MR, A] = CombinedShapeInstanceEncoder.instance(left = self, right = other)
  }

  implicit class ShapeEncoderOps[M : Emptible, A](self: ShapeEncoder[M, A]) {

    def combine[M1 : Emptible, MR](other: ShapeEncoder[M1, A])(
      implicit
      combiner: Combiner[M, M1, MR]
    ): ShapeEncoder[MR, A] = CombinedShapeEncoder.instance(left = self, right = other)

    def combine[M1 : Emptible, MR](other: ShapeInstanceEncoder[M1, A])(
      implicit
      combiner: Combiner[M, M1, MR]
    ): ShapeInstanceEncoder[MR, A] = CombinedShapeInstanceEncoder.instance(left = self, right = other)
  }
}
