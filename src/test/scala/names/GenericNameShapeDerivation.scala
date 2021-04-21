package dev.vgerasimov.shapelse
package names

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericNameShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import names.implicits.all._

  test("Name shape for list of int should be derivable") {
    val encoder = namesShapeEncoder[List[Int]]
    val expected = ListShape("", List(IntShape("")))
    encoder.encode shouldBe expected
    forAll { (a: List[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Name shape for list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[List[C]]
    val expected = ListShape(
      "",
      List(
        ProductShape(
          "",
          List(
            IntShape("i"),
            StringShape("s"),
            BooleanShape("b")
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Name shape for list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = namesShapeEncoder[List[T]]
    val expected = ListShape(
      "",
      List(
        CoproductShape(
          "",
          List(
            ProductShape("A", List(IntShape("i"))),
            ProductShape("B", List(StringShape("s")))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[T]) => encoder.encode(a) shouldBe expected }
  }
}
