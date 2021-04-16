package dev.vgerasimov.shapelse
package annotations
package implicits

trait PrimitiveAnnotatedShapeEncoders {
  import PrimitiveAnnotatedShapeEncoder.instance

  implicit def booleanAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Boolean] =
    instance(BooleanShape(None))
  implicit def charAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Char] =
    instance(CharShape(None))
  implicit def stringAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, String] =
    instance(StringShape(None))
  implicit def byteAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Byte] =
    instance(ByteShape(None))
  implicit def shortAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Short] =
    instance(ShortShape(None))
  implicit def intAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Int] =
    instance(IntShape(None))
  implicit def longAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Long] =
    instance(LongShape(None))
  implicit def floatAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Float] =
    instance(FloatShape(None))
  implicit def doubleAnnotatedShapeEncoder[A]: PrimitiveAnnotatedShapeEncoder[A, Double] =
    instance(DoubleShape(None))
}
