package dev.vgerasimov.scmc
package implicits

trait PrimitiveFieldEncoders {
  implicit val booleanFieldEncoder: FieldEncoder[Boolean] = FieldEncoder.instance(BooleanField)
  implicit val charFieldEncoder: FieldEncoder[Char] = FieldEncoder.instance(CharField)
  implicit val stringFieldEncoder: FieldEncoder[String] = FieldEncoder.instance(StringField)
  implicit val shortFieldEncoder: FieldEncoder[Short] = FieldEncoder.instance(ShortField)
  implicit val intFieldEncoder: FieldEncoder[Int] = FieldEncoder.instance(IntField)
  implicit val longFieldEncoder: FieldEncoder[Long] = FieldEncoder.instance(LongField)
  implicit val floatFieldEncoder: FieldEncoder[Float] = FieldEncoder.instance(FloatField)
  implicit val doubleFieldEncoder: FieldEncoder[Double] = FieldEncoder.instance(DoubleField)
}
