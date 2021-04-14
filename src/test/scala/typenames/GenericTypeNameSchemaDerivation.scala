package dev.vgerasimov.shapelse
package typenames

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericTypeNameSchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import typenames.implicits.all._

  test("Type name schema for list of int should be derivable") {
    val encoder = typeNamesSchemaEncoder[List[Int]]
    val expected = ListSchema("List[Int]", List(IntSchema("Int")))
    encoder.encode shouldBe expected
    forAll { (a: List[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Type name schema for list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = typeNamesSchemaEncoder[List[C]]
    val expected = ListSchema(
      "List[C]",
      List(
        ProductSchema(
          "C",
          List(
            IntSchema("Int"),
            StringSchema("String"),
            BooleanSchema("Boolean")
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Type name schema for list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = typeNamesSchemaEncoder[List[T]]
    val expected = ListSchema(
      "List[T]",
      List(
        CoproductSchema(
          "T",
          List(
            ProductSchema("A", List(IntSchema("Int"))),
            ProductSchema("B", List(StringSchema("String")))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[T]) => encoder.encode(a) shouldBe expected }
  }
}
