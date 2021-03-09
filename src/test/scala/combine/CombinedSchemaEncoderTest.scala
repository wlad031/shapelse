package dev.vgerasimov.shapelse
package combine

import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.StaticAnnotation
import scala.collection.immutable.ListMap

class CombinedSchemaEncoderTest extends AnyFunSuite {

  import annotations.implicits.all._
  import combine.implicits.tuples._
  import empty.implicits.emptibleOption
  import structure.implicits.all._

  private case class a1() extends StaticAnnotation
  private case class a2() extends StaticAnnotation

  test("Combining of two encoders providing boolean schemas should be successful") {
    val enc1 = annotationSchemaEncoder[a1, Boolean]
    val enc2 = annotationSchemaEncoder[a2, Boolean]
    val enc = enc1.combine(enc2)
    val schema = enc.encode
    assert(schema === BooleanSchema((None, None)))
  }

  test("Combining of two encoders providing product schema with primitives only should be successful") {
    @a1() @a2() case class C(@a1() i: Int, @a1() @a2() s: String)

    val enc1 = annotationSchemaEncoder[a1, C]
    val enc2 = annotationSchemaEncoder[a2, C]
    val enc = enc1.combine(enc2)
    val schema = enc.encode
    assert(
      schema === ProductSchema(
          (Some(a1()), Some(a2())),
          ListMap[Symbol, Schema[(Option[a1], Option[a2])]](
            Symbol("i") -> IntSchema((Some(a1()), None)),
            Symbol("s") -> StringSchema((Some(a1()), Some(a2())))
          )
        )
    )
  }

  test("Combining of two encoders providing coproduct schema should be successful") {
    @a1() @a2() sealed trait T
    @a1() case class A() extends T
    @a1() @a2() case class B() extends T

    val enc1 = annotationSchemaEncoder[a1, T]
    val enc2 = annotationSchemaEncoder[a2, T]
    val enc = enc1.combine(enc2)
    val schema = enc.encode
    assert(
      schema === CoproductSchema(
          (Some(a1()), Some(a2())),
          ListMap[Symbol, Schema[(Option[a1], Option[a2])]](
            Symbol("A") -> ProductSchema((Some(a1()), None), ListMap()),
            Symbol("B") -> ProductSchema((Some(a1()), Some(a2())), ListMap())
          )
        )
    )
  }
}
