package dev.vgerasimov.shapelse
package annotations

import org.scalacheck.{Arbitrary, ScalacheckShapeless}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.annotation.StaticAnnotation

//noinspection TypeAnnotation
class ProductAnnotatedShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import annotations.implicits.all._

  case class ann0() extends StaticAnnotation
  case class ann1(i: Int) extends StaticAnnotation

  test("Annotated (zero) shape for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann0, C]
    val expected = ProductShape(None, List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (ann0) shape for case class containing nothing should be derivable") {
    @ann0()
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann0, C]
    val expected = ProductShape(Some(ann0()), List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (ann1) shape for case class containing nothing should be derivable") {
    @ann1(5)
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann1, C]
    val expected = ProductShape(Some(ann1(5)), List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (zero) shape for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann0, C]
    val expected = ProductShape(
      None,
      List(
        IntShape(None),
        StringShape(None),
        BooleanShape(None),
        IntShape(None)
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (ann0) shape for case class containing only primitives should be derivable") {
    @ann0() case class C(i: Int, @ann0() s: String, @ann1(5) b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann0, C]
    val expected = ProductShape(
      Some(ann0()),
      List[Shape[Option[ann0]]](
        IntShape(None),
        StringShape(Some(ann0())),
        BooleanShape(None),
        IntShape(None)
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (ann1) shape for case class containing only primitives should be derivable") {
    @ann0() @ann1(10) case class C(i: Int, @ann0() s: String, @ann1(5) b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann1, C]
    val expected = ProductShape(
      Some(ann1(10)),
      List[Shape[Option[ann1]]](
        IntShape(None),
        StringShape(None),
        BooleanShape(Some(ann1(5))),
        IntShape(None)
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (zero) shape for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann0, C]
    val expected =
      ProductShape(
        None,
        List[Shape[Option[ann0]]](
          StringShape(None),
          ProductShape(
            None,
            List(
              IntShape(None),
              BooleanShape(None)
            )
          ),
          IntShape(None)
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Annotated (ann1) shape for case class containing nested case class should be derivable") {
    @ann1(1) case class C1(@ann1(2) k: Int)
    case class C2(l: Long, d: Double)
    @ann1(3) case class C3(@ann1(4) s: Short)
    @ann1(5) case class C(n: C1, @ann1(6) m: C2, m2: C2, @ann1(7) p: C3)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = annotationShapeEncoder[ann1, C]
    val expected =
      ProductShape(
        Some(ann1(5)),
        List[Shape[Option[ann1]]](
          ProductShape(
            Some(ann1(1)),
            List(IntShape(Some(ann1(2))))
          ),
          ProductShape(
            Some(ann1(6)),
            List(LongShape(None), DoubleShape(None))
          ),
          ProductShape(
            None,
            List(LongShape(None), DoubleShape(None))
          ),
          ProductShape(
            Some(ann1(7)),
            List(ShortShape(Some(ann1(4))))
          ),
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }
}
