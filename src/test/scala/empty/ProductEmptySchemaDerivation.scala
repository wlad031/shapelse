package dev.vgerasimov.shapelse
package empty

import org.scalacheck.{Arbitrary, ScalacheckShapeless}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class ProductEmptySchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._

  test("Empty schema for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptySchemaEncoder[C]
    val expected = ProductSchema(Empty, List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptySchemaEncoder[C]
    val expected = ProductSchema(
      Empty,
      List(
        IntSchema(Empty),
        StringSchema(Empty),
        BooleanSchema(Empty),
        IntSchema(Empty)
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptySchemaEncoder[C]
    val expected =
      ProductSchema(
        Empty,
        List(
          StringSchema(Empty),
          ProductSchema(
            Empty,
            List(
              IntSchema(Empty),
              BooleanSchema(Empty)
            )
          ),
          IntSchema(Empty)
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }
}
