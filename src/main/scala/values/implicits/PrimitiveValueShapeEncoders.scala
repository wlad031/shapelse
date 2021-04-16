package dev.vgerasimov.shapelse
package values
package implicits

trait PrimitiveValueShapeEncoders {
  import PrimitiveValueShapeEncoder.instance

  implicit def booleanValueShapeEncoder: PrimitiveValueShapeEncoder[Boolean] =
    instance((a: Boolean) => BooleanShape(BooleanValue(a)))
  implicit def charValueShapeEncoder: PrimitiveValueShapeEncoder[Char] =
    instance((a: Char) => CharShape(CharValue(a)))
  implicit def stringValueShapeEncoder: PrimitiveValueShapeEncoder[String] =
    instance((a: String) => StringShape(StringValue(a)))
  implicit def byteValueShapeEncoder: PrimitiveValueShapeEncoder[Byte] =
    instance((a: Byte) => ByteShape(ByteValue(a)))
  implicit def shortValueShapeEncoder: PrimitiveValueShapeEncoder[Short] =
    instance((a: Short) => ShortShape(ShortValue(a)))
  implicit def intValueShapeEncoder: PrimitiveValueShapeEncoder[Int] =
    instance((a: Int) => IntShape(IntValue(a)))
  implicit def longValueShapeEncoder: PrimitiveValueShapeEncoder[Long] =
    instance((a: Long) => LongShape(LongValue(a)))
  implicit def floatValueShapeEncoder: PrimitiveValueShapeEncoder[Float] =
    instance((a: Float) => FloatShape(FloatValue(a)))
  implicit def doubleValueShapeEncoder: PrimitiveValueShapeEncoder[Double] =
    instance((a: Double) => DoubleShape(DoubleValue(a)))
}
