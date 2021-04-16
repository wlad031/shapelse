package dev.vgerasimov.shapelse
package values

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CoproductValueShapeDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._
  import values.implicits.all._

  test("Value shape for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = valueShapeEncoder[T]
    forAll { (a: A) =>
      encoder.encode(a) shouldBe CoproductShape(
        CoproductValue,
        List[Shape[Value]](
          ProductShape(ProductValue, List(IntShape(IntValue(a.i)))),
          ProductShape(NilValue, List(StringShape(NilValue)))
        )
      )
    }
    forAll { (a: B) =>
      encoder.encode(a) shouldBe CoproductShape(
        CoproductValue,
        List[Shape[Value]](
          ProductShape(NilValue, List(IntShape(NilValue))),
          ProductShape(ProductValue, List(StringShape(StringValue(a.s))))
        )
      )
    }
  }
}
