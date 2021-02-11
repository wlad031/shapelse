package dev.vgerasimov.scmc
package implicits

/** Contains implicits for [[Field]] derivation for primitive types. */
trait PrimitiveFieldEncoders {
  implicit val booleanFieldEncoder: PrimitiveFieldEncoder[Boolean] = PrimitiveFieldEncoder.instance(BooleanField)
  implicit val charFieldEncoder: PrimitiveFieldEncoder[Char] = PrimitiveFieldEncoder.instance(CharField)
  implicit val stringFieldEncoder: PrimitiveFieldEncoder[String] = PrimitiveFieldEncoder.instance(StringField)
  implicit val shortFieldEncoder: PrimitiveFieldEncoder[Short] = PrimitiveFieldEncoder.instance(ShortField)
  implicit val intFieldEncoder: PrimitiveFieldEncoder[Int] = PrimitiveFieldEncoder.instance(IntField)
  implicit val longFieldEncoder: PrimitiveFieldEncoder[Long] = PrimitiveFieldEncoder.instance(LongField)
  implicit val floatFieldEncoder: PrimitiveFieldEncoder[Float] = PrimitiveFieldEncoder.instance(FloatField)
  implicit val doubleFieldEncoder: PrimitiveFieldEncoder[Double] = PrimitiveFieldEncoder.instance(DoubleField)
}
