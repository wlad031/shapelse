package dev.vgerasimov.shapelse
package annotations

import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.StaticAnnotation

class PrimitiveAnnotatedFieldsDerivation extends AnyFunSuite {

  import annotations.implicits.all._
  import structure.implicits.all._
  import empty.implicits.emptibleOption
  
  private case class annotation() extends StaticAnnotation

  test("Boolean annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Boolean].encode === BooleanField[Option[annotation]](None))
  }

  test("Char annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Char].encode === CharField[Option[annotation]](None))
  }

  test("String annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, String].encode === StringField[Option[annotation]](None))
  }

  test("Short annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Short].encode === ShortField[Option[annotation]](None))
  }

  test("Int annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Int].encode === IntField[Option[annotation]](None))
  }

  test("Long annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Long].encode === LongField[Option[annotation]](None))
  }

  test("Float annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Float].encode === FloatField[Option[annotation]](None))
  }

  test("Double annotated field should be derivable") {
    assert(annotationFieldEncoder[annotation, Double].encode === DoubleField[Option[annotation]](None))
  }

 
}
