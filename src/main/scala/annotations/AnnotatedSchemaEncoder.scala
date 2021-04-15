package dev.vgerasimov.shapelse
package annotations

trait AnnotatedSchemaEncoder[A, T] extends SchemaEncoder[Option[A], T]

object AnnotatedSchemaEncoder {
  def instance[A, T](schema: => Schema[Option[A]]): AnnotatedSchemaEncoder[A, T] = new AnnotatedSchemaEncoder[A, T] {
    override def encode: Schema[Option[A]] = schema
  }
}

trait PrimitiveAnnotatedSchemaEncoder[A, T] extends AnnotatedSchemaEncoder[A, T] {
  override def encode: PrimitiveSchema[Option[A]]
}

object PrimitiveAnnotatedSchemaEncoder {
  def instance[A, T](schema: => PrimitiveSchema[Option[A]]): PrimitiveAnnotatedSchemaEncoder[A, T] =
    new PrimitiveAnnotatedSchemaEncoder[A, T] {
      override def encode: PrimitiveSchema[Option[A]] = schema
    }
}

trait ProductAnnotatedSchemaEncoder[A, T] extends AnnotatedSchemaEncoder[A, T] {
  override def encode: ProductSchema[Option[A]]
}

object ProductAnnotatedSchemaEncoder {
  def instance[A, T](schema: => ProductSchema[Option[A]]): ProductAnnotatedSchemaEncoder[A, T] =
    new ProductAnnotatedSchemaEncoder[A, T] {
      override def encode: ProductSchema[Option[A]] = schema
    }
}

trait CoproductAnnotatedSchemaEncoder[A, T] extends AnnotatedSchemaEncoder[A, T] {
  override def encode: CoproductSchema[Option[A]]
}

object CoproductAnnotatedSchemaEncoder {
  def instance[A, T](schema: => CoproductSchema[Option[A]]): CoproductAnnotatedSchemaEncoder[A, T] =
    new CoproductAnnotatedSchemaEncoder[A, T] {
      override def encode: CoproductSchema[Option[A]] = schema
    }
}
