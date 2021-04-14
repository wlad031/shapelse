package dev.vgerasimov.shapelse
package values

import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class ProductValueSchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import dev.vgerasimov.shapelse.empty.implicits.all._
  import values.implicits.all._

  test("Value schema for case class containing nothing should be derivable") {
    case class C()
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueSchemaEncoder[C]
    forAll { (a: C) => encoder.encode(a) shouldBe ProductSchema(ProductValue, List()) }
  }

  test("Value schema for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueSchemaEncoder[C]
    forAll { (a: C) =>
      encoder.encode(a) shouldBe
      ProductSchema(
        ProductValue,
        List[Schema[Value]](
          IntSchema(IntValue(a.i)),
          StringSchema(StringValue(a.s)),
          BooleanSchema(BooleanValue(a.b)),
          IntSchema(IntValue(a.i2))
        )
      )
    }
  }

  test("Value schema for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueSchemaEncoder[C]
    forAll { (a: C) =>
      encoder.encode(a) shouldBe
      ProductSchema(
        ProductValue,
        List[Schema[Value]](
          StringSchema(StringValue(a.s)),
          ProductSchema(
            ProductValue,
            List[Schema[Value]](
              IntSchema(IntValue(a.n.k)),
              BooleanSchema(BooleanValue(a.n.b))
            )
          ),
          IntSchema(IntValue(a.i))
        )
      )
    }
  }
}
