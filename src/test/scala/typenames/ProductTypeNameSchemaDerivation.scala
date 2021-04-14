package dev.vgerasimov.shapelse
package typenames

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class ProductTypeNameSchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import typenames.implicits.all._

  test("Type name schema for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = typeNamesSchemaEncoder[C]
    val expected = ProductSchema("C", List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Type name schema for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = typeNamesSchemaEncoder[C]
    val expected = ProductSchema(
      "C",
      List(
        IntSchema("Int"),
        StringSchema("String"),
        BooleanSchema("Boolean"),
        IntSchema("Int")
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Type name schema for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = typeNamesSchemaEncoder[C]
    val expected =
      ProductSchema(
        "C",
        List(
          StringSchema("String"),
          ProductSchema(
            "C1",
            List(
              IntSchema("Int"),
              BooleanSchema("Boolean")
            )
          ),
          IntSchema("Int")
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }
}
