package dev.vgerasimov.shapelse
package combine
package implicits

import dev.vgerasimov.shapelse.empty.Emptible

trait EncodersCombiningOps {

  implicit class SchemaInstanceEncoderOps[M : Emptible, A](self: SchemaInstanceEncoder[M, A]) {

    def combine[M1 : Emptible, MR](other: SchemaInstanceEncoder[M1, A])(
      implicit
      metaCombiner: Combiner[M, M1, MR]
    ): SchemaInstanceEncoder[MR, A] = CombinedSchemaInstanceEncoder.instance(left = self, right = other)
  }

  implicit class SchemaEncoderOps[M : Emptible, A](self: SchemaEncoder[M, A]) {

    def combine[M1 : Emptible, MR](other: SchemaEncoder[M1, A])(
      implicit
      combiner: Combiner[M, M1, MR]
    ): SchemaEncoder[MR, A] = CombinedSchemaEncoder.instance(left = self, right = other)

    def combine[M1 : Emptible, MR](other: SchemaInstanceEncoder[M1, A])(
      implicit
      combiner: Combiner[M, M1, MR]
    ): SchemaInstanceEncoder[MR, A] = CombinedSchemaInstanceEncoder.instance(left = self, right = other)
  }
}
