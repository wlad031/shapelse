package dev.vgerasimov.shapelse
package values

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class ProductValueShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._
  import values.implicits.all._

  test("Value shape for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueShapeEncoder[C]
    forAll { (a: C) => encoder.encode(a) shouldBe ProductShape(ProductValue, List()) }
  }

  test("Value shape for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueShapeEncoder[C]
    forAll { (a: C) =>
      encoder.encode(a) shouldBe
      ProductShape(
        ProductValue,
        List[Shape[Value]](
          IntShape(IntValue(a.i)),
          StringShape(StringValue(a.s)),
          BooleanShape(BooleanValue(a.b)),
          IntShape(IntValue(a.i2))
        )
      )
    }
  }

  test("Value shape for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueShapeEncoder[C]
    forAll { (a: C) =>
      encoder.encode(a) shouldBe
      ProductShape(
        ProductValue,
        List[Shape[Value]](
          StringShape(StringValue(a.s)),
          ProductShape(
            ProductValue,
            List[Shape[Value]](
              IntShape(IntValue(a.n.k)),
              BooleanShape(BooleanValue(a.n.b))
            )
          ),
          IntShape(IntValue(a.i))
        )
      )
    }
  }
}
