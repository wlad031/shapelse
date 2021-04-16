package dev.vgerasimov.shapelse
package empty

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CoproductEmptyShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._

  test("Empty shape for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = emptyShapeEncoder[T]
    val expected = CoproductShape(
      Empty,
      List(
        ProductShape(Empty, List(IntShape(Empty))),
        ProductShape(Empty, List(StringShape(Empty)))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }
}
