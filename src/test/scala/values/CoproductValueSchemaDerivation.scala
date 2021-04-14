package dev.vgerasimov.shapelse
package values

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CoproductValueSchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._
  import values.implicits.all._

  test("Value schema for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = valueSchemaEncoder[T]
    forAll { (a: A) =>
      encoder.encode(a) shouldBe CoproductSchema(
        CoproductValue,
        List[Schema[Value]](
          ProductSchema(ProductValue, List(IntSchema(IntValue(a.i)))),
          ProductSchema(NilValue, List(StringSchema(NilValue)))
        )
      )
    }
    forAll { (a: B) =>
      encoder.encode(a) shouldBe CoproductSchema(
        CoproductValue,
        List[Schema[Value]](
          ProductSchema(NilValue, List(IntSchema(NilValue))),
          ProductSchema(ProductValue, List(StringSchema(StringValue(a.s))))
        )
      )
    }
  }
}
