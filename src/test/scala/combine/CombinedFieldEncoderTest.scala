package dev.vgerasimov.shapelse
package combine

import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.StaticAnnotation
import scala.collection.immutable.ListMap

class CombinedFieldEncoderTest extends AnyFunSuite {

  import Emptible.emptibleOption
  import annotations.implicits.all._
  import combine.implicits.tuples._
  import structure.implicits.all._

  private case class a1() extends StaticAnnotation
  private case class a2() extends StaticAnnotation

  test("Combining of two encoders providing boolean fields should be successful") {
    val enc1 = annotationFieldEncoder[a1, Boolean]
    val enc2 = annotationFieldEncoder[a2, Boolean]
    val enc = enc1.combine(enc2)
    val field = enc.encode
    assert(field === BooleanField((None, None)))
  }

  test("Combining of two encoders providing product field with primitives only should be successful") {
    @a1() @a2() case class C(@a1() i: Int, @a1() @a2() s: String)

    val enc1 = annotationFieldEncoder[a1, C]
    val enc2 = annotationFieldEncoder[a2, C]
    val enc = enc1.combine(enc2)
    val field = enc.encode
    assert(
      field === ProductField(
          (Some(a1()), Some(a2())),
          ListMap[Symbol, Field[(Option[a1], Option[a2])]](
            Symbol("i") -> IntField((Some(a1()), None)),
            Symbol("s") -> StringField((Some(a1()), Some(a2())))
          )
        )
    )
  }

  test("Combining of two encoders providing coproduct field should be successful") {
    @a1() @a2() sealed trait T
    @a1() case class A() extends T
    @a1() @a2() case class B() extends T

    val enc1 = annotationFieldEncoder[a1, T]
    val enc2 = annotationFieldEncoder[a2, T]
    val enc = enc1.combine(enc2)
    val field = enc.encode
    assert(
      field === CoproductField(
        (Some(a1()), Some(a2())),
        ListMap[Symbol, Field[(Option[a1], Option[a2])]](
          Symbol("A") -> ProductField((Some(a1()), None), ListMap()),
          Symbol("B") -> ProductField((Some(a1()), Some(a2())), ListMap())
        )
      )
    )
  }
}
