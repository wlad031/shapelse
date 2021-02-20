package dev.vgerasimov.shapelse
package types

import org.scalatest.funsuite.AnyFunSuite

class  PrimitiveFieldsDerivation extends AnyFunSuite {

  import structure.implicits.primitives._

  test("Boolean field should be derivable") {
    assert(structureFieldEncoder[Boolean].encode === BooleanField(Empty()))
  }

  test("Char field should be derivable") {
    assert(structureFieldEncoder[Char].encode === CharField(Empty()))
  }

  test("String field should be derivable") {
    assert(structureFieldEncoder[String].encode === StringField(Empty()))
  }

  test("Short field should be derivable") {
    assert(structureFieldEncoder[Short].encode === ShortField(Empty()))
  }

  test("Int field should be derivable") {
    assert(structureFieldEncoder[Int].encode === IntField(Empty()))
  }

  test("Long field should be derivable") {
    assert(structureFieldEncoder[Long].encode === LongField(Empty()))
  }

  test("Float field should be derivable") {
    assert(structureFieldEncoder[Float].encode === FloatField(Empty()))
  }

  test("Double field should be derivable") {
    assert(structureFieldEncoder[Double].encode === DoubleField(Empty()))
  }


}
