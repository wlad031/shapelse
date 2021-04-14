package dev.vgerasimov.shapelse
package typenames

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveTypeNameSchemaDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import typenames.implicits.primitives._

  test("Boolean type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Boolean]
    val expected = BooleanSchema("Boolean")
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Char]
    val expected = CharSchema("Char")
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[String]
    val expected = StringSchema("String")
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Byte]
    val expected = ByteSchema("Byte")
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Short]
    val expected = ShortSchema("Short")
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Int]
    val expected = IntSchema("Int")
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Long]
    val expected = LongSchema("Long")
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Float]
    val expected = FloatSchema("Float")
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double type name schema should be derivable") {
    val encoder = typeNamesSchemaEncoder[Double]
    val expected = DoubleSchema("Double")
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
