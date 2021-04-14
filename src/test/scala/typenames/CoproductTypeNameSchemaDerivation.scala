package dev.vgerasimov.shapelse
package typenames

import org.scalacheck.{Arbitrary, ScalacheckShapeless}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CoproductTypeNameSchemaDerivation
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import typenames.implicits.all._

  test("Type name schema for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = typeNamesSchemaEncoder[T]
    val expected = CoproductSchema(
      "T",
      List(
        ProductSchema("A", List(IntSchema("Int"))),
        ProductSchema("B", List(StringSchema("String")))
      )
    )
    encoder.encode shouldBe expected
    forAll { (a: T) => encoder.encode(a) shouldBe expected }
  }
}
