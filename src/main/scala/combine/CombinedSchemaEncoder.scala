package dev.vgerasimov.shapelse
package combine

import dev.vgerasimov.shapelse.empty.Emptible

private[shapelse] trait CombinedSchemaInstanceEncoder[M, A] extends SchemaInstanceEncoder[M, A]
//noinspection ConvertExpressionToSAM
private[shapelse] object CombinedSchemaInstanceEncoder {

  def instance[M1 : Emptible, M2 : Emptible, MR, A](
    left: SchemaInstanceEncoder[M1, A],
    right: SchemaInstanceEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedSchemaInstanceEncoder[MR, A] = new CombinedSchemaInstanceEncoder[MR, A] {
    override def encode(a: A): Schema[MR] = Schema.combine(left.encode(a), right.encode(a))
  }
}

private[shapelse] trait CombinedSchemaEncoder[M, A] extends SchemaEncoder[M, A]
private[shapelse] object CombinedSchemaEncoder {

  def instance[M1 : Emptible, M2 : Emptible, MR, A](
    left: SchemaEncoder[M1, A],
    right: SchemaEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedSchemaEncoder[MR, A] =
    new CombinedSchemaEncoder[MR, A] {
      override def encode: Schema[MR] = Schema.combine(left.encode, right.encode)
    }
}
