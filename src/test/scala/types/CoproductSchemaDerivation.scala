package dev.vgerasimov.shapelse
package types

import empty.Empty

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class CoproductSchemaDerivation extends AnyFunSuite {

  import structure.implicits.all._

  test("Schema for trait containing nothing should not be derivable") {
    assertDoesNotCompile("""
        |structureSchemaEncoder[T].encode
        |""".stripMargin)
  }

  test("Schema for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    val expected = ListMap(
      Symbol("A") -> ProductSchema.empty(ListMap(Symbol("i") -> IntSchema.empty)),
      Symbol("B") -> ProductSchema.empty(ListMap(Symbol("s") -> StringSchema.empty))
    )
    structureSchemaEncoder[T].encode match {
      case CoproductSchema(_: Empty, childs) => assert(childs === expected)
      case x                                 => fail(s"Unexpected schema: $x")
    }
  }
}
