package dev.vgerasimov.shapelse
package names

trait NameSchemaEncoder[A] extends SchemaEncoder[String, A]

object NameSchemaEncoder {
  def instance[A, T](schema: => Schema[String]): NameSchemaEncoder[A] = new NameSchemaEncoder[A] {
    override def encode: Schema[String] = schema
  }
}

trait PrimitiveNameSchemaEncoder[A] extends NameSchemaEncoder[A] {
  override def encode: PrimitiveSchema[String]
}

object PrimitiveNameSchemaEncoder {
  def instance[A, T](schema: => PrimitiveSchema[String]): PrimitiveNameSchemaEncoder[A] =
    new PrimitiveNameSchemaEncoder[A] {
      override def encode: PrimitiveSchema[String] = schema
    }
}

trait ProductNameSchemaEncoder[A] extends NameSchemaEncoder[A] {
  override def encode: ProductSchema[String]
}

object ProductNameSchemaEncoder {
  def instance[A](schema: => ProductSchema[String]): ProductNameSchemaEncoder[A] =
    new ProductNameSchemaEncoder[A] {
      override def encode: ProductSchema[String] = schema
    }
}

trait CoproductNameSchemaEncoder[A] extends NameSchemaEncoder[A] {
  override def encode: CoproductSchema[String]
}

object CoproductNameSchemaEncoder {
  def instance[A](schema: => CoproductSchema[String]): CoproductNameSchemaEncoder[A] =
    new CoproductNameSchemaEncoder[A] {
      override def encode: CoproductSchema[String] = schema
    }
}
