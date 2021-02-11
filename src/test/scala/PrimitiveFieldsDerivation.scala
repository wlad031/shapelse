package dev.vgerasimov.scmc

import implicits.primitives._

import org.scalatest.funsuite.AnyFunSuite

class PrimitiveFieldsDerivation extends AnyFunSuite {

  test("Boolean field should be derivable") {
    assert(Field.derive[Boolean] === BooleanField)
  }

  test("Char field should be derivable") {
    assert(Field.derive[Char] === CharField)
  }

  test("String field should be derivable") {
    assert(Field.derive[String] === StringField)
  }

  test("Short field should be derivable") {
    assert(Field.derive[Short] === ShortField)
  }

  test("Int field should be derivable") {
    assert(Field.derive[Int] === IntField)
  }

  test("Long field should be derivable") {
    assert(Field.derive[Long] === LongField)
  }

  test("Float field should be derivable") {
    assert(Field.derive[Float] === FloatField)
  }

  test("Double field should be derivable") {
    assert(Field.derive[Double] === DoubleField)
  }
}
