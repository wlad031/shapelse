package dev.vgerasimov.shapelse
package names

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericNameSchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import names.implicits.all._

  test("Name schema for list of int should be derivable") {
    val encoder = namesSchemaEncoder[List[Int]]
    val expected = ListSchema("", List(IntSchema("")))
    encoder.encode shouldBe expected
    forAll { (a: List[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Name schema for option of int should be derivable") {
    val encoder = namesSchemaEncoder[Option[Int]]
    val expected = OptionSchema("", Some(IntSchema("")))
    encoder.encode shouldBe expected
    forAll { (a: Option[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Name schema for list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesSchemaEncoder[List[C]]
    val expected = ListSchema(
      "",
      List(
        ProductSchema(
          "",
          List(
            IntSchema("i"),
            StringSchema("s"),
            BooleanSchema("b")
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Name schema for option of product should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesSchemaEncoder[Option[C]]
    val expected = OptionSchema(
      "",
      Some(
        ProductSchema(
          "",
          List(
            IntSchema("i"),
            StringSchema("s"),
            BooleanSchema("b")
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: Option[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Name schema for list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = namesSchemaEncoder[List[T]]
    val expected = ListSchema(
      "",
      List(
        CoproductSchema(
          "",
          List(
            ProductSchema("A", List(IntSchema("i"))),
            ProductSchema("B", List(StringSchema("s")))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[T]) => encoder.encode(a) shouldBe expected }
  }

  test("Name schema for option of coproduct should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = namesSchemaEncoder[Option[T]]
    val expected = OptionSchema(
      "",
      Some(
        CoproductSchema(
          "",
          List(
            ProductSchema("A", List(IntSchema("i"))),
            ProductSchema("B", List(StringSchema("s")))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: Option[T]) => encoder.encode(a) shouldBe expected }
  }
}
