package dev.vgerasimov.shapelse
package names

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveNameShapeDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import names.implicits.primitives._

  test("Boolean name shape should be derivable") {
    val encoder = namesShapeEncoder[Boolean]
    val expected = BooleanShape("")
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char name shape should be derivable") {
    val encoder = namesShapeEncoder[Char]
    val expected = CharShape("")
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String name shape should be derivable") {
    val encoder = namesShapeEncoder[String]
    val expected = StringShape("")
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte name shape should be derivable") {
    val encoder = namesShapeEncoder[Byte]
    val expected = ByteShape("")
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short name shape should be derivable") {
    val encoder = namesShapeEncoder[Short]
    val expected = ShortShape("")
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int name shape should be derivable") {
    val encoder = namesShapeEncoder[Int]
    val expected = IntShape("")
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long name shape should be derivable") {
    val encoder = namesShapeEncoder[Long]
    val expected = LongShape("")
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float name shape should be derivable") {
    val encoder = namesShapeEncoder[Float]
    val expected = FloatShape("")
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double name shape should be derivable") {
    val encoder = namesShapeEncoder[Double]
    val expected = DoubleShape("")
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
