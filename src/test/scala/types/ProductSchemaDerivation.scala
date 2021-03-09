package dev.vgerasimov.shapelse
package types

import empty.Empty

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class ProductSchemaDerivation extends AnyFunSuite {

  import structure.implicits.all._

  test("Schema for case class containing nothing should be derivable") {
    case class C()
    structureSchemaEncoder[C].encode match {
      case ProductSchema(_: Empty, childs) => assert(childs === ListMap())
      case x                               => fail(s"Unexpected schema: $x")
    }
  }

  test("Schema for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    val expected = ListMap(
      Symbol("i")  -> IntSchema.empty,
      Symbol("s")  -> StringSchema.empty,
      Symbol("b")  -> BooleanSchema.empty,
      Symbol("i2") -> IntSchema.empty
    )
    structureSchemaEncoder[C].encode match {
      case ProductSchema(_: Empty, childs) => assert(childs === expected)
      case x                               => fail(s"Unexpected schema: $x")
    }
  }

  test("Schema for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    val expected =
      ListMap(
        Symbol("s") -> StringSchema.empty,
        Symbol("n") -> ProductSchema.empty(
          ListMap(
            Symbol("k") -> IntSchema.empty,
            Symbol("b") -> BooleanSchema.empty
          )
        ),
        Symbol("i") -> IntSchema.empty
      )
    structureSchemaEncoder[C].encode match {
      case ProductSchema(_: Empty, childs) => assert(childs === expected)
      case x                               => fail(s"Unexpected schema: $x")
    }
  }
}
