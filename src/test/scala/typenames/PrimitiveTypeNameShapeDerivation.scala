package dev.vgerasimov.shapelse
package typenames

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveTypeNameShapeDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import typenames.implicits.primitives._

  test("Boolean type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Boolean]
    val expected = BooleanShape("Boolean")
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Char]
    val expected = CharShape("Char")
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[String]
    val expected = StringShape("String")
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Byte]
    val expected = ByteShape("Byte")
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Short]
    val expected = ShortShape("Short")
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Int]
    val expected = IntShape("Int")
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Long]
    val expected = LongShape("Long")
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Float]
    val expected = FloatShape("Float")
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double type name shape should be derivable") {
    val encoder = typeNamesShapeEncoder[Double]
    val expected = DoubleShape("Double")
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
