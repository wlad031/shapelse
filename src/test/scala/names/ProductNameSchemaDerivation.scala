package dev.vgerasimov.shapelse
package names

import org.scalatest.funsuite.AnyFunSuite

class ProductNameSchemaDerivation extends AnyFunSuite {

  import names.implicits.all._

  test("Name schema for case class containing nothing should be derivable") {
    case class C()
    namesSchemaEncoder[C].encode match {
      case ProductSchema("", childs) => assert(childs === List())
      case x                         => fail(s"Unexpected schema: $x")
    }
  }

  test("Name schema for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    val expected = List(
      IntSchema("i"),
      StringSchema("s"),
      BooleanSchema("b"),
      IntSchema("i2")
    )
    namesSchemaEncoder[C].encode match {
      case ProductSchema("", childs) => assert(childs === expected)
      case x                         => fail(s"Unexpected schema: $x")
    }
  }

  test("Name schema for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    val expected =
      List(
        StringSchema("s"),
        ProductSchema(
          "n",
          List(
            IntSchema(""),
            BooleanSchema("")
          )
        ),
        IntSchema("")
      )
    namesSchemaEncoder[C].encode match {
      case ProductSchema("", childs) => assert(childs === expected)
      case x                         => fail(s"Unexpected schema: $x")
    }
  }
}
