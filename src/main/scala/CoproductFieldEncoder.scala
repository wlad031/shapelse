package dev.vgerasimov.scmc

trait CoproductFieldEncoder[A] extends FieldEncoder[A] {
  def encode: CoproductField
}

object CoproductFieldEncoder {
  def instance[A](field: CoproductField): CoproductFieldEncoder[A] = new CoproductFieldEncoder[A] {
    override val encode: CoproductField = field
  }
}
