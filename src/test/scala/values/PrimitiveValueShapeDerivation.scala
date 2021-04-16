package dev.vgerasimov.shapelse
package values

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveValueShapeDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import dev.vgerasimov.shapelse.empty.implicits.primitives._
  import values.implicits.primitives._

  test("Boolean value shape should be derivable") {
    val encoder = valueShapeEncoder[Boolean]
    forAll { (a: Boolean) => encoder.encode(a) shouldBe BooleanShape(BooleanValue(a)) }
  }

  test("Char value shape should be derivable") {
    val encoder = valueShapeEncoder[Char]
    forAll { (a: Char) => encoder.encode(a) shouldBe CharShape(CharValue(a)) }
  }

  test("String value shape should be derivable") {
    val encoder = valueShapeEncoder[String]
    forAll { (a: String) => encoder.encode(a) shouldBe StringShape(StringValue(a)) }
  }

  test("Byte value shape should be derivable") {
    val encoder = valueShapeEncoder[Byte]
    forAll { (a: Byte) => encoder.encode(a) shouldBe ByteShape(ByteValue(a)) }
  }

  test("Short value shape should be derivable") {
    val encoder = valueShapeEncoder[Short]
    forAll { (a: Short) => encoder.encode(a) shouldBe ShortShape(ShortValue(a)) }
  }

  test("Int value shape should be derivable") {
    val encoder = valueShapeEncoder[Int]
    forAll { (a: Int) => encoder.encode(a) shouldBe IntShape(IntValue(a)) }
  }

  test("Long value shape should be derivable") {
    val encoder = valueShapeEncoder[Long]
    forAll { (a: Long) => encoder.encode(a) shouldBe LongShape(LongValue(a)) }
  }

  test("Float value shape should be derivable") {
    val encoder = valueShapeEncoder[Float]
    forAll { (a: Float) => encoder.encode(a) shouldBe FloatShape(FloatValue(a)) }
  }

  test("Double value shape should be derivable") {
    val encoder = valueShapeEncoder[Double]
    forAll { (a: Double) => encoder.encode(a) shouldBe DoubleShape(DoubleValue(a)) }
  }
}
