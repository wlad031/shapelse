package dev.vgerasimov.scmc

trait PrimitiveFieldEncoder[A] extends FieldEncoder[A] {
  def encode: PrimitiveField
}

object PrimitiveFieldEncoder {
  def instance[A](field: PrimitiveField): PrimitiveFieldEncoder[A] = new PrimitiveFieldEncoder[A] {
    override val encode: PrimitiveField = field
  }
}
