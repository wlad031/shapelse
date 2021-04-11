package dev.vgerasimov.shapelse
package annotations

trait AnnotatedSchemaEncoder[A, T] extends SchemaEncoder[Option[A], T]

object AnnotatedSchemaEncoder {
  def instance[A, T](schema: => Schema[Option[A]]): AnnotatedSchemaEncoder[A, T] = new AnnotatedSchemaEncoder[A, T] {
    override def encode: Schema[Option[A]] = schema
  }
}
