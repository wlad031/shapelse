package dev.vgerasimov.scmc

trait FieldEncoder[-A] {
  def encode: Field
}

object FieldEncoder {
  def instance[A](field: => Field): FieldEncoder[A] = new FieldEncoder[A] {
    override lazy val encode: Field = field
  }
}
