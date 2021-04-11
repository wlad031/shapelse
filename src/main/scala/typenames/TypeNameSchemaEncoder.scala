package dev.vgerasimov.shapelse
package typenames

trait TypeNameSchemaEncoder[A] extends SchemaEncoder[String, A]

object TypeNameSchemaEncoder {
  def instance[A, T](schema: => Schema[String]): TypeNameSchemaEncoder[A] = new TypeNameSchemaEncoder[A] {
    override def encode: Schema[String] = schema
  }
}

trait PrimitiveTypeNameSchemaEncoder[A] extends TypeNameSchemaEncoder[A] {
  override def encode: PrimitiveSchema[String]
}

object PrimitiveTypeNameSchemaEncoder {
  def instance[A, T](schema: => PrimitiveSchema[String]): PrimitiveTypeNameSchemaEncoder[A] =
    new PrimitiveTypeNameSchemaEncoder[A] {
      override def encode: PrimitiveSchema[String] = schema
    }
}

trait ProductTypeNameSchemaEncoder[A] extends TypeNameSchemaEncoder[A] {
  override def encode: ProductSchema[String]
}

object ProductTypeNameSchemaEncoder {
  def instance[A](schema: => ProductSchema[String]): ProductTypeNameSchemaEncoder[A] =
    new ProductTypeNameSchemaEncoder[A] {
      override def encode: ProductSchema[String] = schema
    }
}

trait CoproductTypeNameSchemaEncoder[A] extends TypeNameSchemaEncoder[A] {
  override def encode: CoproductSchema[String]
}

object CoproductTypeNameSchemaEncoder {
  def instance[A](schema: => CoproductSchema[String]): CoproductTypeNameSchemaEncoder[A] =
    new CoproductTypeNameSchemaEncoder[A] {
      override def encode: CoproductSchema[String] = schema
    }
}
