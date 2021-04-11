package dev.vgerasimov.shapelse
package values

trait ValueSchemaEncoder[A] extends SchemaInstanceEncoder[Value, A]

object ValueSchemaEncoder {
  def instance[A](f: A => Schema[Value]): ValueSchemaEncoder[A] = (a: A) => f(a)
}

trait PrimitiveValueSchemaEncoder[A] extends ValueSchemaEncoder[A] {
  override def encode(a: A): PrimitiveSchema[Value]
}

object PrimitiveValueSchemaEncoder {
  def instance[A](schema: A => PrimitiveSchema[Value]): PrimitiveValueSchemaEncoder[A] = (a: A) => schema(a)
}

trait ProductValueSchemaEncoder[A] extends ValueSchemaEncoder[A] {
  def encode(a: A): ProductSchema[Value]
}

object ProductValueSchemaEncoder {
  def instance[A](schema: A => ProductSchema[Value]): ProductValueSchemaEncoder[A] = (a: A) => schema(a)
}

trait CoproductValueSchemaEncoder[A] extends ValueSchemaEncoder[A] {
  def encode(a: A): CoproductSchema[Value]
}

object CoproductValueSchemaEncoder {
  def instance[A](schema: A => CoproductSchema[Value]): CoproductValueSchemaEncoder[A] = (a: A) => schema(a)
}
