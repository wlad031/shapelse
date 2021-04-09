package dev.vgerasimov.shapelse
package structure
package implicits

import empty.Emptible

/** Contains implicits for [[Schema]] derivation for primitive types. */
trait PrimitiveSchemaEncoders {
  implicit def booleanSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Boolean] =
    PrimitiveSchemaEncoder.instance(BooleanSchema(Emptible.summon.empty))
  implicit def charSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Char] =
    PrimitiveSchemaEncoder.instance(CharSchema(Emptible.summon.empty))
  implicit def stringSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, String] =
    PrimitiveSchemaEncoder.instance(StringSchema(Emptible.summon.empty))
  implicit def byteSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Byte] =
    PrimitiveSchemaEncoder.instance(ShortSchema(Emptible.summon.empty))
  implicit def shortSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Short] =
    PrimitiveSchemaEncoder.instance(ShortSchema(Emptible.summon.empty))
  implicit def intSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Int] =
    PrimitiveSchemaEncoder.instance(IntSchema(Emptible.summon.empty))
  implicit def longSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Long] =
    PrimitiveSchemaEncoder.instance(LongSchema(Emptible.summon.empty))
  implicit def floatSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Float] =
    PrimitiveSchemaEncoder.instance(FloatSchema(Emptible.summon.empty))
  implicit def doubleSchemaEncoder[M : Emptible]: PrimitiveSchemaEncoder[M, Double] =
    PrimitiveSchemaEncoder.instance(DoubleSchema(Emptible.summon.empty))
}
