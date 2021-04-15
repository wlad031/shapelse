package dev.vgerasimov.shapelse
package annotations
package implicits

trait PrimitiveAnnotatedSchemaEncoders {
  import PrimitiveAnnotatedSchemaEncoder.instance

  implicit def booleanAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Boolean] =
    instance(BooleanSchema(None))
  implicit def charAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Char] =
    instance(CharSchema(None))
  implicit def stringAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, String] =
    instance(StringSchema(None))
  implicit def byteAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Byte] =
    instance(ByteSchema(None))
  implicit def shortAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Short] =
    instance(ShortSchema(None))
  implicit def intAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Int] =
    instance(IntSchema(None))
  implicit def longAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Long] =
    instance(LongSchema(None))
  implicit def floatAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Float] =
    instance(FloatSchema(None))
  implicit def doubleAnnotatedSchemaEncoder[A]: PrimitiveAnnotatedSchemaEncoder[A, Double] =
    instance(DoubleSchema(None))
}
