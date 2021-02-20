package dev.vgerasimov.shapelse

trait FieldEncoder[M, A] {
  def encode: Field[M]

  def combine[M1, MR](that: FieldEncoder[M1, A])(
    implicit metaCombiner: Combiner[M, M1, MR]
  ): CombinedFieldEncoder[MR, A] = CombinedFieldEncoder.instance[M, M1, MR, A](this, that)
}

object FieldEncoder {
  def instance[M, A](field: => Field[M]): FieldEncoder[M, A] = new FieldEncoder[M, A] {
    override lazy val encode: Field[M] = field
  }
}

trait PrimitiveFieldEncoder[M, A] extends FieldEncoder[M, A] {
  override def encode: PrimitiveField[M]
}

object PrimitiveFieldEncoder {
  def instance[M, A](field: PrimitiveField[M]): PrimitiveFieldEncoder[M, A] = new PrimitiveFieldEncoder[M, A] {
    override val encode: PrimitiveField[M] = field
  }
}

trait ProductFieldEncoder[M, A] extends FieldEncoder[M, A] {
  override def encode: ProductField[M]
}

object ProductFieldEncoder {
  def instance[M, A](field: ProductField[M]): ProductFieldEncoder[M, A] = new ProductFieldEncoder[M, A] {
    override val encode: ProductField[M] = field
  }
}

trait CoproductFieldEncoder[M, A] extends FieldEncoder[M, A] {
  override def encode: CoproductField[M]
}

object CoproductFieldEncoder {
  def instance[M, A](field: CoproductField[M]): CoproductFieldEncoder[M, A] = new CoproductFieldEncoder[M, A] {
    override val encode: CoproductField[M] = field
  }
}
