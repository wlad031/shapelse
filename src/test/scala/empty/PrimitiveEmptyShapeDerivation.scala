package dev.vgerasimov.shapelse
package empty

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveEmptyShapeDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import dev.vgerasimov.shapelse.empty.implicits.primitives._

  test("Boolean empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Boolean]
    val expected = BooleanShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Char]
    val expected = CharShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String empty shape should be derivable") {
    val encoder = emptyShapeEncoder[String]
    val expected = StringShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Byte]
    val expected = ByteShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Short]
    val expected = ShortShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Int]
    val expected = IntShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Long]
    val expected = LongShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Float]
    val expected = FloatShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double empty shape should be derivable") {
    val encoder = emptyShapeEncoder[Double]
    val expected = DoubleShape(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
