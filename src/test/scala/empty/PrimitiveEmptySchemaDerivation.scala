package dev.vgerasimov.shapelse
package empty

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class PrimitiveEmptySchemaDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import dev.vgerasimov.shapelse.empty.implicits.primitives._

  test("Boolean empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Boolean]
    val expected = BooleanSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Char]
    val expected = CharSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String empty schema should be derivable") {
    val encoder = emptySchemaEncoder[String]
    val expected = StringSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Byte]
    val expected = ByteSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Short]
    val expected = ShortSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Int]
    val expected = IntSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Long]
    val expected = LongSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Float]
    val expected = FloatSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double empty schema should be derivable") {
    val encoder = emptySchemaEncoder[Double]
    val expected = DoubleSchema(Empty)
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
