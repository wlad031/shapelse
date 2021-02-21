package dev.vgerasimov.shapelse
package types

import org.scalatest.funsuite.AnyFunSuite

class PrimitiveFieldsDerivation extends AnyFunSuite {

  import structure.implicits.primitives._

  test("Boolean field should be derivable") {
    assert(structureFieldEncoder[Boolean].encode === BooleanField.empty)
  }

  test("Char field should be derivable") {
    assert(structureFieldEncoder[Char].encode === CharField.empty)
  }

  test("String field should be derivable") {
    assert(structureFieldEncoder[String].encode === StringField.empty)
  }

  test("Short field should be derivable") {
    assert(structureFieldEncoder[Short].encode === ShortField.empty)
  }

  test("Int field should be derivable") {
    assert(structureFieldEncoder[Int].encode === IntField.empty)
  }

  test("Long field should be derivable") {
    assert(structureFieldEncoder[Long].encode === LongField.empty)
  }

  test("Float field should be derivable") {
    assert(structureFieldEncoder[Float].encode === FloatField.empty)
  }

  test("Double field should be derivable") {
    assert(structureFieldEncoder[Double].encode === DoubleField.empty)
  }
}
