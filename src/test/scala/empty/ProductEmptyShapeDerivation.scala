package dev.vgerasimov.shapelse
package empty

import org.scalacheck.{Arbitrary, ScalacheckShapeless}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class ProductEmptyShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._

  test("Empty shape for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptyShapeEncoder[C]
    val expected = ProductShape(Empty, List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Empty shape for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptyShapeEncoder[C]
    val expected = ProductShape(
      Empty,
      List(
        IntShape(Empty),
        StringShape(Empty),
        BooleanShape(Empty),
        IntShape(Empty)
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Empty shape for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptyShapeEncoder[C]
    val expected =
      ProductShape(
        Empty,
        List(
          StringShape(Empty),
          ProductShape(
            Empty,
            List(
              IntShape(Empty),
              BooleanShape(Empty)
            )
          ),
          IntShape(Empty)
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }
}
