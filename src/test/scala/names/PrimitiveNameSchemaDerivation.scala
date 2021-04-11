package dev.vgerasimov.shapelse
package names

import org.scalatest.funsuite.AnyFunSuite

class PrimitiveNameSchemaDerivation extends AnyFunSuite {

  import names.implicits.primitives._

  test("Boolean name schema should be derivable") {
    assert(namesSchemaEncoder[Boolean].encode === BooleanSchema(""))
  }

  test("Char name schema should be derivable") {
    assert(namesSchemaEncoder[Char].encode === CharSchema(""))
  }

  test("String name schema should be derivable") {
    assert(namesSchemaEncoder[String].encode === StringSchema(""))
  }

  test("Byte name schema should be derivable") {
    assert(namesSchemaEncoder[Byte].encode === ByteSchema(""))
  }

  test("Short name schema should be derivable") {
    assert(namesSchemaEncoder[Short].encode === ShortSchema(""))
  }

  test("Int name schema should be derivable") {
    assert(namesSchemaEncoder[Int].encode === IntSchema(""))
  }

  test("Long name schema should be derivable") {
    assert(namesSchemaEncoder[Long].encode === LongSchema(""))
  }

  test("Float name schema should be derivable") {
    assert(namesSchemaEncoder[Float].encode === FloatSchema(""))
  }

  test("Double name schema should be derivable") {
    assert(namesSchemaEncoder[Double].encode === DoubleSchema(""))
  }
}
