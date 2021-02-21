package dev.vgerasimov.shapelse
package types

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class ProductFieldsDerivation extends AnyFunSuite {

  import structure.implicits.all._

  test("Field for case class containing nothing should be derivable") {
    case class C()
    structureFieldEncoder[C].encode match {
      case ProductField(_: Empty, childs) => assert(childs === ListMap())
      case x                              => fail(s"Unexpected field: $x")
    }
  }

  test("Field for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    val expected = ListMap(
      Symbol("i")  -> IntField.empty,
      Symbol("s")  -> StringField.empty,
      Symbol("b")  -> BooleanField.empty,
      Symbol("i2") -> IntField.empty
    )
    structureFieldEncoder[C].encode match {
      case ProductField(_: Empty, childs) => assert(childs === expected)
      case x                              => fail(s"Unexpected field: $x")
    }
  }

  test("Field for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    val expected =
      ListMap(
        Symbol("s") -> StringField.empty,
        Symbol("n") -> ProductField.empty(
          ListMap(
            Symbol("k") -> IntField.empty,
            Symbol("b") -> BooleanField.empty
          )
        ),
        Symbol("i") -> IntField.empty
      )
    structureFieldEncoder[C].encode match {
      case ProductField(_: Empty, childs) => assert(childs === expected)
      case x                              => fail(s"Unexpected field: $x")
    }
  }
}
