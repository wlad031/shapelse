package dev.vgerasimov.shapelse
package empty

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericEmptyShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._

  test("Empty shape for list of int should be derivable") {
    val encoder = emptyShapeEncoder[List[Int]]
    val expected = ListShape(Empty, List(IntShape(Empty)))
    encoder.encode shouldBe expected
    forAll { (a: List[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty shape for list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptyShapeEncoder[List[C]]
    val expected = ListShape(
      Empty,
      List(
        ProductShape(
          Empty,
          List(
            IntShape(Empty),
            StringShape(Empty),
            BooleanShape(Empty)
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty shape for list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = emptyShapeEncoder[List[T]]
    val expected = ListShape(
      Empty,
      List(
        CoproductShape(
          Empty,
          List(
            ProductShape(Empty, List(IntShape(Empty))),
            ProductShape(Empty, List(StringShape(Empty)))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[T]) => encoder.encode(a) shouldBe expected }
  }
}
