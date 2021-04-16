package dev.vgerasimov.shapelse
package typenames

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericTypeNameShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import typenames.implicits.all._

  test("Type name shape for list of int should be derivable") {
    val encoder = typeNamesShapeEncoder[List[Int]]
    val expected = ListShape("List[Int]", List(IntShape("Int")))
    encoder.encode shouldBe expected
    forAll { (a: List[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Type name shape for list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = typeNamesShapeEncoder[List[C]]
    val expected = ListShape(
      "List[C]",
      List(
        ProductShape(
          "C",
          List(
            IntShape("Int"),
            StringShape("String"),
            BooleanShape("Boolean")
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Type name shape for list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = typeNamesShapeEncoder[List[T]]
    val expected = ListShape(
      "List[T]",
      List(
        CoproductShape(
          "T",
          List(
            ProductShape("A", List(IntShape("Int"))),
            ProductShape("B", List(StringShape("String")))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[T]) => encoder.encode(a) shouldBe expected }
  }
}
