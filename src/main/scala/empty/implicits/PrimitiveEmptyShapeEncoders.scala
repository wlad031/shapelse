package dev.vgerasimov.shapelse
package empty
package implicits

trait PrimitiveEmptyShapeEncoders {
  import PrimitiveShapeEncoder.instance

  implicit val booleanEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Boolean] = instance(BooleanShape(Empty))
  implicit val charEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Char] = instance(CharShape(Empty))
  implicit val stringEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, String] = instance(StringShape(Empty))
  implicit val byteEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Byte] = instance(ByteShape(Empty))
  implicit val shortEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Short] = instance(ShortShape(Empty))
  implicit val intEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Int] = instance(IntShape(Empty))
  implicit val longEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Long] = instance(LongShape(Empty))
  implicit val floatEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Float] = instance(FloatShape(Empty))
  implicit val doubleEmptyShapeEncoder: PrimitiveShapeEncoder[Empty.type, Double] = instance(DoubleShape(Empty))
}
