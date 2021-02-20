package dev.vgerasimov.shapelse
package types

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class ProductFieldsDerivations extends AnyFunSuite with FieldsDerivationSuite {

  import structure.implicits.all._

  test("Field for case class containing nothing should be derivable") {
    case class C()
    structureFieldEncoder[C].encode match {
      case ProductField(_: Empty, childs) => assertFields[Empty](childs, ListMap())
      case x                              => fail(s"Unexpected field: $x")
    }
  }

  test("Field for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    val expected = ListMap(
      "i"  -> IntField(Empty()),
      "s"  -> StringField(Empty()),
      "b"  -> BooleanField(Empty()),
      "i2" -> IntField(Empty())
    )
    structureFieldEncoder[C].encode match {
      case ProductField(_: Empty, childs) => assertFields(childs, expected)
      case x                              => fail(s"Unexpected field: $x")
    }
  }

  ignore("Field for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    val expected =
      ListMap("s" -> StringField(Empty()), "n" -> ProductField(Empty(), ListMap()), "i" -> IntField(Empty()))
    structureFieldEncoder[C].encode match {
      case ProductField(_: Empty, childs) => assertFields(childs, expected)
      case x                              => fail(s"Unexpected field: $x")
    }
  }
}
