package dev.vgerasimov.shapelse
package combine

import dev.vgerasimov.shapelse.empty.Emptible

private[shapelse] class CombinedSchemaInstanceEncoder[M1 : Emptible, M2 : Emptible, MR, A] private (
  left: SchemaInstanceEncoder[M1, A],
  right: SchemaInstanceEncoder[M2, A]
)(
  implicit
  combiner: Combiner[M1, M2, MR]
) extends SchemaInstanceEncoder[MR, A] {
  override def encode(a: A): Schema[MR] = Schema.combine(left.encode(a), right.encode(a))
}

private[shapelse] object CombinedSchemaInstanceEncoder {

  def instance[M1 : Emptible, M2 : Emptible, MR, A](
    left: SchemaInstanceEncoder[M1, A],
    right: SchemaInstanceEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedSchemaInstanceEncoder[M1, M2, MR, A] = new CombinedSchemaInstanceEncoder(left, right)
}

private[shapelse] class CombinedSchemaEncoder[M1 : Emptible, M2 : Emptible, MR, A] private (
  left: SchemaEncoder[M1, A],
  right: SchemaEncoder[M2, A]
)(
  implicit
  combiner: Combiner[M1, M2, MR]
) extends SchemaEncoder[MR, A] {
  override def encode: Schema[MR] = Schema.combine(left.encode, right.encode)
}

private[shapelse] object CombinedSchemaEncoder {

  def instance[M1 : Emptible, M2 : Emptible, MR, A](
    left: SchemaEncoder[M1, A],
    right: SchemaEncoder[M2, A]
  )(
    implicit
    combiner: Combiner[M1, M2, MR]
  ): CombinedSchemaEncoder[M1, M2, MR, A] = new CombinedSchemaEncoder(left, right)
}
