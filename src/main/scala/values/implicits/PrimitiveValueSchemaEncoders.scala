package dev.vgerasimov.shapelse
package values
package implicits

trait PrimitiveValueSchemaEncoders {
  import PrimitiveValueSchemaEncoder.instance

  implicit def booleanValueSchemaEncoder: PrimitiveValueSchemaEncoder[Boolean] =
    instance((a: Boolean) => BooleanSchema(BooleanValue(a)))
  implicit def charValueSchemaEncoder: PrimitiveValueSchemaEncoder[Char] =
    instance((a: Char) => CharSchema(CharValue(a)))
  implicit def stringValueSchemaEncoder: PrimitiveValueSchemaEncoder[String] =
    instance((a: String) => StringSchema(StringValue(a)))
  implicit def byteValueSchemaEncoder: PrimitiveValueSchemaEncoder[Byte] =
    instance((a: Byte) => ByteSchema(ByteValue(a)))
  implicit def shortValueSchemaEncoder: PrimitiveValueSchemaEncoder[Short] =
    instance((a: Short) => ShortSchema(ShortValue(a)))
  implicit def intValueSchemaEncoder: PrimitiveValueSchemaEncoder[Int] =
    instance((a: Int) => IntSchema(IntValue(a)))
  implicit def longValueSchemaEncoder: PrimitiveValueSchemaEncoder[Long] =
    instance((a: Long) => LongSchema(LongValue(a)))
  implicit def floatValueSchemaEncoder: PrimitiveValueSchemaEncoder[Float] =
    instance((a: Float) => FloatSchema(FloatValue(a)))
  implicit def doubleValueSchemaEncoder: PrimitiveValueSchemaEncoder[Double] =
    instance((a: Double) => DoubleSchema(DoubleValue(a)))
}
