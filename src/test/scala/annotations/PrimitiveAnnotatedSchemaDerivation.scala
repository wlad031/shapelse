package dev.vgerasimov.shapelse
package annotations

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.annotation.StaticAnnotation

class PrimitiveAnnotatedSchemaDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import annotations.implicits.primitives._

  case class ann() extends StaticAnnotation

  test("Boolean annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Boolean]
    val expected = BooleanSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Char]
    val expected = CharSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, String]
    val expected = StringSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Byte]
    val expected = ByteSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Short]
    val expected = ShortSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Int]
    val expected = IntSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Long]
    val expected = LongSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Float]
    val expected = FloatSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double annotation schema should be derivable") {
    val encoder = annotationSchemaEncoder[ann, Double]
    val expected = DoubleSchema(None)
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
