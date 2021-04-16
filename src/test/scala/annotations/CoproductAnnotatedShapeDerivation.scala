package dev.vgerasimov.shapelse
package annotations

import org.scalacheck.{Arbitrary, ScalacheckShapeless}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.annotation.StaticAnnotation

//noinspection TypeAnnotation
class CoproductAnnotatedShapeDerivation
  extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import annotations.implicits.all._

  case class ann0() extends StaticAnnotation
  case class ann1(i: Int) extends StaticAnnotation

  test("Annotated(zero) shape for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = annotationShapeEncoder[ann0, T]
    val expected = CoproductShape(
      None,
      List(
        ProductShape(None, List(IntShape(None))),
        ProductShape(None, List(StringShape(None)))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated(ann0) shape for trait containing two simple case classes should be derivable") {
    @ann0 sealed trait T
    case class A(@ann0 i: Int) extends T
    @ann0 case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = annotationShapeEncoder[ann0, T]
    val expected = CoproductShape(
      Some(ann0()),
      List[Shape[Option[ann0]]](
        ProductShape(None, List(IntShape(Some(ann0())))),
        ProductShape(Some(ann0()), List(StringShape(None)))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated(ann1) shape for trait containing two simple case classes should be derivable") {
    @ann0 @ann1(1) sealed trait T
    @ann1(5) case class A(@ann0 i: Int) extends T
    @ann0 case class B(@ann1(10) s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = annotationShapeEncoder[ann1, T]
    val expected = CoproductShape(
      Some(ann1(1)),
      List[Shape[Option[ann1]]](
        ProductShape(Some(ann1(5)), List(IntShape(None))),
        ProductShape(None, List(StringShape(Some(ann1(10)))))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }
}
