package dev.vgerasimov.shapelse
package values

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class GenericValueShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._
  import values.implicits.all._

  test("Value shape for non-empty list of int should be derivable") {
    val encoder = valueShapeEncoder[List[Int]]
    forAll(Arbitrary.arbitrary[List[Int]].suchThat(_.nonEmpty)) { (a: List[Int]) =>
      encoder.encode(a) shouldBe
      ListShape(
        ListValue,
        a.map(x => IntShape(IntValue(x)): Shape[Value])
      )
    }
  }

  test("Value shape for empty list of int should be derivable") {
    val encoder = valueShapeEncoder[List[Int]]
    encoder.encode(List()) shouldBe ListShape(NilValue, List(IntShape(NilValue): Shape[Value]))
  }

  test("Value shape for non-empty list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    val encoder = valueShapeEncoder[List[C]]
    forAll(Arbitrary.arbitrary[List[C]].suchThat(_.nonEmpty)) { (a: List[C]) =>
      encoder.encode(a) shouldBe ListShape(
        ListValue,
        a.map(x =>
          ProductShape(
            ProductValue,
            List(
              IntShape(IntValue(x.i)),
              StringShape(StringValue(x.s)),
              BooleanShape(BooleanValue(x.b))
            )
          ): Shape[Value]
        )
      )
    }
  }

  test("Value shape for empty list of products should be derivable") {
    case class C(i: Int, s: String, b: Boolean)
    val encoder = valueShapeEncoder[List[C]]
    encoder.encode(List()) shouldBe ListShape(
      NilValue,
      List(
        ProductShape(
          NilValue,
          List(
            IntShape(NilValue),
            StringShape(NilValue),
            BooleanShape(NilValue)
          )
        ): Shape[Value]
      )
    )
  }

  test("Value shape for non-empty list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    val encoder = valueShapeEncoder[List[T]]

    forAll(Arbitrary.arbitrary[List[A]].suchThat(_.nonEmpty)) { (a: List[A]) =>
      encoder.encode(a) shouldBe ListShape(
        ListValue,
        a.map(x =>
          CoproductShape(
            CoproductValue,
            List(
              ProductShape(ProductValue, List(IntShape(IntValue(x.i)))),
              ProductShape(NilValue, List(StringShape(NilValue)))
            )
          ): Shape[Value]
        )
      )
    }
    forAll(Arbitrary.arbitrary[List[B]].suchThat(_.nonEmpty)) { (a: List[B]) =>
      encoder.encode(a) shouldBe ListShape(
        ListValue,
        a.map(x =>
          CoproductShape(
            CoproductValue,
            List(
              ProductShape(NilValue, List(IntShape(NilValue))),
              ProductShape(ProductValue, List(StringShape(StringValue(x.s))))
            )
          ): Shape[Value]
        )
      )
    }
  }

  test("Value shape for empty list of coproducts should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    val encoder = valueShapeEncoder[List[T]]

    encoder.encode(List()) shouldBe ListShape(
      NilValue,
      List(
        CoproductShape(
          NilValue,
          List[Shape[Value]](
            ProductShape(NilValue, List(IntShape(NilValue))),
            ProductShape(NilValue, List(StringShape(NilValue)))
          )
        )
      )
    )
  }
}
