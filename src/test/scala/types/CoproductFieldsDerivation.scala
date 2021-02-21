package dev.vgerasimov.shapelse
package types

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class CoproductFieldsDerivation extends AnyFunSuite {

  import structure.implicits.all._

  test("Field for trait containing nothing should not be derivable") {
    assertDoesNotCompile(
      """
        |structureFieldEncoder[T].encode
        |""".stripMargin)
  }

  test("Field for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    val expected = ListMap(
      Symbol("A") -> ProductField.empty(ListMap(Symbol("i") -> IntField.empty)),
      Symbol("B") -> ProductField.empty(ListMap(Symbol("s") -> StringField.empty))
    )
    structureFieldEncoder[T].encode match {
      case CoproductField(_: Empty, childs) => assert(childs === expected)
      case x                                => fail(s"Unexpected field: $x")
    }
  }
}
