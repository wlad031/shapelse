package dev.vgerasimov.shapelse
package names

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveNameSchemaDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import names.implicits.primitives._

  test("Boolean name schema should be derivable") {
    val encoder = namesSchemaEncoder[Boolean]
    val expected = BooleanSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char name schema should be derivable") {
    val encoder = namesSchemaEncoder[Char]
    val expected = CharSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String name schema should be derivable") {
    val encoder = namesSchemaEncoder[String]
    val expected = StringSchema("")
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte name schema should be derivable") {
    val encoder = namesSchemaEncoder[Byte]
    val expected = ByteSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short name schema should be derivable") {
    val encoder = namesSchemaEncoder[Short]
    val expected = ShortSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int name schema should be derivable") {
    val encoder = namesSchemaEncoder[Int]
    val expected = IntSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long name schema should be derivable") {
    val encoder = namesSchemaEncoder[Long]
    val expected = LongSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float name schema should be derivable") {
    val encoder = namesSchemaEncoder[Float]
    val expected = FloatSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double name schema should be derivable") {
    val encoder = namesSchemaEncoder[Double]
    val expected = DoubleSchema("")
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
