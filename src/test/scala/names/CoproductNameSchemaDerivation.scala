package dev.vgerasimov.shapelse
package names

import org.scalatest.funsuite.AnyFunSuite

class CoproductNameSchemaDerivation extends AnyFunSuite {
/*
  import names.implicits.all._

  test("Name schema for trait containing nothing should not be derivable") {
    assertDoesNotCompile("""
        |nameSchemaEncoder[T].encode
        |""".stripMargin)
  }

  test("Name schema for trait containing two simple case classes should be derivable") {
    sealed trait T
    case class A(i: Int) extends T
    case class B(s: String) extends T

    val expected = List(
      ProductSchema("A", List(IntSchema("i"))),
      ProductSchema("B", List(StringSchema("s")))
    )
    namesSchemaEncoder[T].encode match {
      case CoproductSchema("", childs) => assert(childs === expected)
      case x                           => fail(s"Unexpected schema: $x")
    }
  }

 */
}
