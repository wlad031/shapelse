package dev.vgerasimov.shapelse
package annotations

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.annotation.StaticAnnotation

class PrimitiveAnnotatedShapeDerivation extends AnyFunSuite with Matchers with ScalaCheckPropertyChecks {

  import annotations.implicits.primitives._

  case class ann() extends StaticAnnotation

  test("Boolean annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Boolean]
    val expected = BooleanShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Boolean) => encoder.encode(a) shouldBe expected }
  }

  test("Char annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Char]
    val expected = CharShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Char) => encoder.encode(a) shouldBe expected }
  }

  test("String annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, String]
    val expected = StringShape(None)
    encoder.encode shouldBe expected
    forAll { (a: String) => encoder.encode(a) shouldBe expected }
  }

  test("Byte annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Byte]
    val expected = ByteShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Byte) => encoder.encode(a) shouldBe expected }
  }

  test("Short annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Short]
    val expected = ShortShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Short) => encoder.encode(a) shouldBe expected }
  }

  test("Int annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Int]
    val expected = IntShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Int) => encoder.encode(a) shouldBe expected }
  }

  test("Long annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Long]
    val expected = LongShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Long) => encoder.encode(a) shouldBe expected }
  }

  test("Float annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Float]
    val expected = FloatShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Float) => encoder.encode(a) shouldBe expected }
  }

  test("Double annotation shape should be derivable") {
    val encoder = annotationShapeEncoder[ann, Double]
    val expected = DoubleShape(None)
    encoder.encode shouldBe expected
    forAll { (a: Double) => encoder.encode(a) shouldBe expected }
  }
}
