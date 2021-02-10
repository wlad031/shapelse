package dev.vgerasimov.scmc

trait ObjectFieldEncoder[A] extends FieldEncoder[A] {
  def encode: ObjectField
}

object ObjectFieldEncoder {
  def instance[A](field: ObjectField): ObjectFieldEncoder[A] = new ObjectFieldEncoder[A] {
    override val encode: ObjectField = field
  }
}
