package dev.vgerasimov.shapelse
package typenames

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CoproductTypeNameShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import typenames.implicits.all._

  test("Type name shape for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = typeNamesShapeEncoder[T]
    val expected = CoproductShape(
      "T",
      List(
        ProductShape("A", List(IntShape("Int"))),
        ProductShape("B", List(StringShape("String")))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }
}
