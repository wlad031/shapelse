package dev.vgerasimov.shapelse
package annotations

import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.StaticAnnotation

class PrimitiveAnnotatedSchemaDerivation extends AnyFunSuite {

  import structure.implicits.primitives._
  import empty.implicits._
  import annotations.implicits.all._

  private case class annotation() extends StaticAnnotation

  test("Boolean annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Boolean].encode === BooleanSchema[Option[annotation]](None))
  }

  test("Char annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Char].encode === CharSchema[Option[annotation]](None))
  }

  test("String annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, String].encode === StringSchema[Option[annotation]](None))
  }

  test("Byte annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Byte].encode === ByteSchema[Option[annotation]](None))
  }

  test("Short annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Short].encode === ShortSchema[Option[annotation]](None))
  }

  test("Int annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Int].encode === IntSchema[Option[annotation]](None))
  }

  test("Long annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Long].encode === LongSchema[Option[annotation]](None))
  }

  test("Float annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Float].encode === FloatSchema[Option[annotation]](None))
  }

  test("Double annotated schema should be derivable") {
    assert(annotationSchemaEncoder[annotation, Double].encode === DoubleSchema[Option[annotation]](None))
  }

}
