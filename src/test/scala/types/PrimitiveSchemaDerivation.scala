package dev.vgerasimov.shapelse
package types

import org.scalatest.funsuite.AnyFunSuite

class PrimitiveSchemaDerivation extends AnyFunSuite {

  import structure.implicits.primitives._

  test("Boolean schema should be derivable") {
    assert(structureSchemaEncoder[Boolean].encode === BooleanSchema.empty)
  }

  test("Char schema should be derivable") {
    assert(structureSchemaEncoder[Char].encode === CharSchema.empty)
  }

  test("String schema should be derivable") {
    assert(structureSchemaEncoder[String].encode === StringSchema.empty)
  }

  test("Short schema should be derivable") {
    assert(structureSchemaEncoder[Short].encode === ShortSchema.empty)
  }

  test("Int schema should be derivable") {
    assert(structureSchemaEncoder[Int].encode === IntSchema.empty)
  }

  test("Long schema should be derivable") {
    assert(structureSchemaEncoder[Long].encode === LongSchema.empty)
  }

  test("Float schema should be derivable") {
    assert(structureSchemaEncoder[Float].encode === FloatSchema.empty)
  }

  test("Double schema should be derivable") {
    assert(structureSchemaEncoder[Double].encode === DoubleSchema.empty)
  }
}
