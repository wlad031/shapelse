package dev.vgerasimov.shapelse
package typenames
package implicits

trait PrimitiveTypeNameShapeEncoders {
  import TypeNameShapeEncoder.instance

  implicit val booleanTypeNameShapeEncoder: TypeNameShapeEncoder[Boolean] = instance(BooleanShape("Boolean"))
  implicit val charTypeNameShapeEncoder: TypeNameShapeEncoder[Char] = instance(CharShape("Char"))
  implicit val stringTypeNameShapeEncoder: TypeNameShapeEncoder[String] = instance(StringShape("String"))
  implicit val byteTypeNameShapeEncoder: TypeNameShapeEncoder[Byte] = instance(ByteShape("Byte"))
  implicit val shortTypeNameShapeEncoder: TypeNameShapeEncoder[Short] = instance(ShortShape("Short"))
  implicit val intTypeNameShapeEncoder: TypeNameShapeEncoder[Int] = instance(IntShape("Int"))
  implicit val longTypeNameShapeEncoder: TypeNameShapeEncoder[Long] = instance(LongShape("Long"))
  implicit val floatTypeNameShapeEncoder: TypeNameShapeEncoder[Float] = instance(FloatShape("Float"))
  implicit val doubleTypeNameShapeEncoder: TypeNameShapeEncoder[Double] = instance(DoubleShape("Double"))
}
