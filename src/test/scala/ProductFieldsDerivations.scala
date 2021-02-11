package dev.vgerasimov.scmc

import implicits.all._

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class ProductFieldsDerivations extends AnyFunSuite with FieldsDerivationSuite {

  test("Field for case class containing nothing should be derivable") {
    case class C()
    Field.derive[C] match {
      case ProductField(childs) => assertFields(childs, ListMap())
      case x                    => fail(s"Unexpected field: $x")
    }
  }

  test("Field for case class containing only primitives should be derivable") {
    case class C(i: Int, s: String, b: Boolean, i2: Int)
    val expected = ListMap("i" -> IntField, "s" -> StringField, "b" -> BooleanField, "i2" -> IntField)
    Field.derive[C] match {
      case ProductField(childs) => assertFields(childs, expected)
      case x                    => fail(s"Unexpected field: $x")
    }
  }

  ignore("Field for case class containing nested case class should be derivable") {
    case class C1(k: Int, b: Boolean)
    case class C(s: String, n: C1, i: Int)
    val expected = ListMap("s" -> StringField, "n" -> ProductField(ListMap()), "i" -> IntField)
    Field.derive[C] match {
      case ProductField(childs) => assertFields(childs, expected)
      case x                    => fail(s"Unexpected field: $x")
    }
  }
}
