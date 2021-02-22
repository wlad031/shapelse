package dev.vgerasimov.shapelse
package combine

trait CombinedFieldEncoder[M, A] extends FieldEncoder[M, A]

object CombinedFieldEncoder {

  def instance[M1, M2, MR, A](left: FieldEncoder[M1, A], right: FieldEncoder[M2, A])(
    implicit metaCombiner: Combiner[M1, M2, MR]
  ): CombinedFieldEncoder[MR, A] =
    new CombinedFieldEncoder[MR, A] {
      override def encode: Field[MR] = left.encode.combine(metaCombiner)(right.encode)
    }
}
