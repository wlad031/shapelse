package dev.vgerasimov.shapelse
package annotations

trait AnnotatedFieldEncoder[A, T] extends FieldEncoder[Option[A], T] {
  def encode: Field[Option[A]]
}

object AnnotatedFieldEncoder {
  def instance[A, T](field: => Field[Option[A]]): AnnotatedFieldEncoder[A, T] = new AnnotatedFieldEncoder[A, T] {
    override def encode: Field[Option[A]] = field
  }
}
