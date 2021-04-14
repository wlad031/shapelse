package dev.vgerasimov.shapelse
package combine

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CombinedSchemaEncoderTest
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import combine.implicits.tuples._
  import combine.syntax._
  import dev.vgerasimov.shapelse.empty.instances._
  import names.implicits.all._
  import typenames.implicits.all._

  test("Combining of name and type name encoders providing product schema with primitives only should be successful") {
    case class C(f: Float, c: Char, l: Long)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesSchemaEncoder[C].combine(typeNamesSchemaEncoder[C])
    val expected =
      ProductSchema(
        ("", "C"),
        List(
          FloatSchema("f", "Float"),
          CharSchema("c", "Char"),
          LongSchema("l", "Long")
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Combining of name and type name encoders providing product schema should be successful") {
    case class C1(f: Float, c: Char, l: Long)
    case class C(s: String, inner: C1, d: Double)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesSchemaEncoder[C].combine(typeNamesSchemaEncoder[C])
    val expected =
      ProductSchema(
        ("", "C"),
        List(
          StringSchema("s", "String"),
          ProductSchema(
            ("inner", "C1"),
            List(
              FloatSchema("f", "Float"),
              CharSchema("c", "Char"),
              LongSchema("l", "Long")
            )
          ),
          DoubleSchema("d", "Double")
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }
}
