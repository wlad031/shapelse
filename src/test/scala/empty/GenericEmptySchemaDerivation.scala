package dev.vgerasimov.shapelse
package empty

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericEmptySchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._

  test("Empty schema for list of int should be derivable") {
    val encoder = emptySchemaEncoder[List[Int]]
    val expected = ListSchema(Empty, List(IntSchema(Empty)))
    encoder.encode shouldBe expected
    forAll { (a: List[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for option of int should be derivable") {
    val encoder = emptySchemaEncoder[Option[Int]]
    val expected = OptionSchema(Empty, Some(IntSchema(Empty)))
    encoder.encode shouldBe expected
    forAll { (a: Option[Int]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptySchemaEncoder[List[C]]
    val expected = ListSchema(
      Empty,
      List(
        ProductSchema(
          Empty,
          List(
            IntSchema(Empty),
            StringSchema(Empty),
            BooleanSchema(Empty)
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for option of product should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = emptySchemaEncoder[Option[C]]
    val expected = OptionSchema(
      Empty,
      Some(
        ProductSchema(
          Empty,
          List(
            IntSchema(Empty),
            StringSchema(Empty),
            BooleanSchema(Empty)
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: Option[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = emptySchemaEncoder[List[T]]
    val expected = ListSchema(
      Empty,
      List(
        CoproductSchema(
          Empty,
          List(
            ProductSchema(Empty, List(IntSchema(Empty))),
            ProductSchema(Empty, List(StringSchema(Empty)))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: List[T]) => encoder.encode(a) shouldBe expected }
  }

  test("Empty schema for option of coproduct should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = emptySchemaEncoder[Option[T]]
    val expected = OptionSchema(
      Empty,
      Some(
        CoproductSchema(
          Empty,
          List(
            ProductSchema(Empty, List(IntSchema(Empty))),
            ProductSchema(Empty, List(StringSchema(Empty)))
          )
        )
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: Option[T]) => encoder.encode(a) shouldBe expected }
  }
}
