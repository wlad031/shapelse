package dev.vgerasimov.shapelse
package empty
package implicits

trait PrimitiveEmptySchemaEncoders {
  import PrimitiveSchemaEncoder.instance

  implicit val booleanEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Boolean] = instance(BooleanSchema(Empty))
  implicit val charEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Char] = instance(CharSchema(Empty))
  implicit val stringEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, String] = instance(StringSchema(Empty))
  implicit val byteEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Byte] = instance(ShortSchema(Empty))
  implicit val shortEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Short] = instance(ShortSchema(Empty))
  implicit val intEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Int] = instance(IntSchema(Empty))
  implicit val longEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Long] = instance(LongSchema(Empty))
  implicit val floatEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Float] = instance(FloatSchema(Empty))
  implicit val doubleEmptySchemaEncoder: PrimitiveSchemaEncoder[Empty.type, Double] = instance(DoubleSchema(Empty))
}
