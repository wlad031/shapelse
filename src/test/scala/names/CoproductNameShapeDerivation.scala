package dev.vgerasimov.shapelse
package names

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CoproductNameShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import names.implicits.all._

  test("Name shape for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = namesShapeEncoder[T]
    val expected = CoproductShape(
      "",
      List(
        ProductShape("A", List(IntShape("i"))),
        ProductShape("B", List(StringShape("s")))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }
}
