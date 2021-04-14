package dev.vgerasimov.shapelse
package values

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveValueSchemaDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import dev.vgerasimov.shapelse.empty.implicits.primitives._
  import values.implicits.primitives._

  test("Boolean value schema should be derivable") {
    val encoder = valueSchemaEncoder[Boolean]
    forAll { (a: Boolean) => encoder.encode(a) shouldBe BooleanSchema(BooleanValue(a)) }
  }

  test("Char value schema should be derivable") {
    val encoder = valueSchemaEncoder[Char]
    forAll { (a: Char) => encoder.encode(a) shouldBe CharSchema(CharValue(a)) }
  }

  test("String value schema should be derivable") {
    val encoder = valueSchemaEncoder[String]
    forAll { (a: String) => encoder.encode(a) shouldBe StringSchema(StringValue(a)) }
  }

  test("Byte value schema should be derivable") {
    val encoder = valueSchemaEncoder[Byte]
    forAll { (a: Byte) => encoder.encode(a) shouldBe ByteSchema(ByteValue(a)) }
  }

  test("Short value schema should be derivable") {
    val encoder = valueSchemaEncoder[Short]
    forAll { (a: Short) => encoder.encode(a) shouldBe ShortSchema(ShortValue(a)) }
  }

  test("Int value schema should be derivable") {
    val encoder = valueSchemaEncoder[Int]
    forAll { (a: Int) => encoder.encode(a) shouldBe IntSchema(IntValue(a)) }
  }

  test("Long value schema should be derivable") {
    val encoder = valueSchemaEncoder[Long]
    forAll { (a: Long) => encoder.encode(a) shouldBe LongSchema(LongValue(a)) }
  }

  test("Float value schema should be derivable") {
    val encoder = valueSchemaEncoder[Float]
    forAll { (a: Float) => encoder.encode(a) shouldBe FloatSchema(FloatValue(a)) }
  }

  test("Double value schema should be derivable") {
    val encoder = valueSchemaEncoder[Double]
    forAll { (a: Double) => encoder.encode(a) shouldBe DoubleSchema(DoubleValue(a)) }
  }
}
