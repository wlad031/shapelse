package dev.vgerasimov.shapelse
package structure
package implicits

/** Contains implicits for [[Field]] derivation for primitive types. */
trait PrimitiveFieldEncoders {
  implicit def booleanFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Boolean] =
    PrimitiveFieldEncoder.instance(BooleanField(Emptible.summon.empty))
  implicit def charFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Char] =
    PrimitiveFieldEncoder.instance(CharField(Emptible.summon.empty))
  implicit def stringFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, String] =
    PrimitiveFieldEncoder.instance(StringField(Emptible.summon.empty))
  implicit def shortFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Short] =
    PrimitiveFieldEncoder.instance(ShortField(Emptible.summon.empty))
  implicit def intFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Int] =
    PrimitiveFieldEncoder.instance(IntField(Emptible.summon.empty))
  implicit def longFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Long] =
    PrimitiveFieldEncoder.instance(LongField(Emptible.summon.empty))
  implicit def floatFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Float] =
    PrimitiveFieldEncoder.instance(FloatField(Emptible.summon.empty))
  implicit def doubleFieldEncoder[M : Emptible]: PrimitiveFieldEncoder[M, Double] =
    PrimitiveFieldEncoder.instance(DoubleField(Emptible.summon.empty))
}
