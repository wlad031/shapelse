package dev.vgerasimov.shapelse
package typenames
package implicits

trait PrimitiveTypeNameSchemaEncoders {
  import TypeNameSchemaEncoder.instance

  implicit val booleanTypeNameSchemaEncoder: TypeNameSchemaEncoder[Boolean] = instance(BooleanSchema("Boolean"))
  implicit val charTypeNameSchemaEncoder: TypeNameSchemaEncoder[Char] = instance(CharSchema("Char"))
  implicit val stringTypeNameSchemaEncoder: TypeNameSchemaEncoder[String] = instance(StringSchema("String"))
  implicit val byteTypeNameSchemaEncoder: TypeNameSchemaEncoder[Byte] = instance(ByteSchema("Byte"))
  implicit val shortTypeNameSchemaEncoder: TypeNameSchemaEncoder[Short] = instance(ShortSchema("Short"))
  implicit val intTypeNameSchemaEncoder: TypeNameSchemaEncoder[Int] = instance(IntSchema("Int"))
  implicit val longTypeNameSchemaEncoder: TypeNameSchemaEncoder[Long] = instance(LongSchema("Long"))
  implicit val floatTypeNameSchemaEncoder: TypeNameSchemaEncoder[Float] = instance(FloatSchema("Float"))
  implicit val doubleTypeNameSchemaEncoder: TypeNameSchemaEncoder[Double] = instance(DoubleSchema("Double"))
}
