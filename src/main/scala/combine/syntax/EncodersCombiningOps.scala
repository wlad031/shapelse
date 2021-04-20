package dev.vgerasimov.shapelse
package combine
package syntax

import dev.vgerasimov.shapelse.MetaProvider

trait EncodersCombiningOps {

  implicit class ShapeInstanceEncoderOps[M : MetaProvider, A](self: ShapeInstanceEncoder[M, A]) {

    def combine[M1 : MetaProvider, MR](other: ShapeInstanceEncoder[M1, A])(
      implicit
      metaCombiner: Combiner[M, M1, MR]
    ): ShapeInstanceEncoder[MR, A] = CombinedShapeInstanceEncoder.instance(left = self, right = other)
  }

  implicit class ShapeEncoderOps[M : MetaProvider, A](self: ShapeEncoder[M, A]) {

    def combine[M1 : MetaProvider, MR](other: ShapeEncoder[M1, A])(
      implicit
      combiner: Combiner[M, M1, MR]
    ): ShapeEncoder[MR, A] = CombinedShapeEncoder.instance(left = self, right = other)

    def combine[M1 : MetaProvider, MR](other: ShapeInstanceEncoder[M1, A])(
      implicit
      combiner: Combiner[M, M1, MR]
    ): ShapeInstanceEncoder[MR, A] = CombinedShapeInstanceEncoder.instance(left = self, right = other)
  }
}
