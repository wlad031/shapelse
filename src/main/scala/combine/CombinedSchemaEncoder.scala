package dev.vgerasimov.shapelse
package combine

trait CombinedSchemaEncoder[M, A] extends SchemaEncoder[M, A]

object CombinedSchemaEncoder {

  def instance[M1, M2, MR, A](left: SchemaEncoder[M1, A], right: SchemaEncoder[M2, A])(
    implicit metaCombiner: Combiner[M1, M2, MR]
  ): CombinedSchemaEncoder[MR, A] =
    new CombinedSchemaEncoder[MR, A] {
      override def encode: Schema[MR] = left.encode.combine(metaCombiner)(right.encode)
    }
}
