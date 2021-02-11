package dev.vgerasimov.scmc

trait ProductFieldEncoder[A] extends FieldEncoder[A] {
  def encode: ProductField
}

object ProductFieldEncoder {
  def instance[A](field: ProductField): ProductFieldEncoder[A] = new ProductFieldEncoder[A] {
    override val encode: ProductField = field
  }
}
