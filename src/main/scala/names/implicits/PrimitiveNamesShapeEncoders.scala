package dev.vgerasimov.shapelse
package names
package implicits

/** Contains implicits for [[Shape]] derivation for primitive types. */
trait PrimitiveNamesShapeEncoders {
  import PrimitiveNameShapeEncoder.instance

  implicit val booleanNameShapeEncoder: PrimitiveNameShapeEncoder[Boolean] = instance(BooleanShape(""))
  implicit val charNameShapeEncoder: PrimitiveNameShapeEncoder[Char] = instance(CharShape(""))
  implicit val stringNameShapeEncoder: PrimitiveNameShapeEncoder[String] = instance(StringShape(""))
  implicit val byteNameShapeEncoder: PrimitiveNameShapeEncoder[Byte] = instance(ByteShape(""))
  implicit val shortNameShapeEncoder: PrimitiveNameShapeEncoder[Short] = instance(ShortShape(""))
  implicit val intNameShapeEncoder: PrimitiveNameShapeEncoder[Int] = instance(IntShape(""))
  implicit val longNameShapeEncoder: PrimitiveNameShapeEncoder[Long] = instance(LongShape(""))
  implicit val floatNameShapeEncoder: PrimitiveNameShapeEncoder[Float] = instance(FloatShape(""))
  implicit val doubleNameShapeEncoder: PrimitiveNameShapeEncoder[Double] = instance(DoubleShape(""))
}
