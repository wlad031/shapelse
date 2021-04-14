package dev.vgerasimov.shapelse
package names
package implicits

/** Contains implicits for [[Schema]] derivation for primitive types. */
trait PrimitiveNamesSchemaEncoders {
  import PrimitiveNameSchemaEncoder.instance

  implicit val booleanNameSchemaEncoder: PrimitiveNameSchemaEncoder[Boolean] = instance(BooleanSchema(""))
  implicit val charNameSchemaEncoder: PrimitiveNameSchemaEncoder[Char] = instance(CharSchema(""))
  implicit val stringNameSchemaEncoder: PrimitiveNameSchemaEncoder[String] = instance(StringSchema(""))
  implicit val byteNameSchemaEncoder: PrimitiveNameSchemaEncoder[Byte] = instance(ByteSchema(""))
  implicit val shortNameSchemaEncoder: PrimitiveNameSchemaEncoder[Short] = instance(ShortSchema(""))
  implicit val intNameSchemaEncoder: PrimitiveNameSchemaEncoder[Int] = instance(IntSchema(""))
  implicit val longNameSchemaEncoder: PrimitiveNameSchemaEncoder[Long] = instance(LongSchema(""))
  implicit val floatNameSchemaEncoder: PrimitiveNameSchemaEncoder[Float] = instance(FloatSchema(""))
  implicit val doubleNameSchemaEncoder: PrimitiveNameSchemaEncoder[Double] = instance(DoubleSchema(""))
}
