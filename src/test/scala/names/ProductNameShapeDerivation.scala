package dev.vgerasimov.shapelse
package names

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class ProductNameShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import names.implicits.all._

  test("Name shape for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[C]
    val expected = ProductShape("", List())
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Name shape for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[C]
    val expected = ProductShape(
      "",
      List(
        IntShape("i"),
        StringShape("s"),
        BooleanShape("b"),
        IntShape("i2")
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Name shape for case class containing Option of Int should be derivable") {
    case class C(o: Option[Int])
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[C]
    val expected = ProductShape(
      "",
      List(
        CoproductShape(
          "o",
          List(
            ProductShape("None", List()),
            ProductShape("Some", List(IntShape("value")))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Name shape for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[C]
    val expected =
      ProductShape(
        "",
        List(
          StringShape("s"),
          ProductShape(
            "n",
            List(
              IntShape("k"),
              BooleanShape("b")
            )
          ),
          IntShape("i")
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }
}
