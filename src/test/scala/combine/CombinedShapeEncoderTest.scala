package dev.vgerasimov.shapelse
package combine

import dev.vgerasimov.shapelse.values._
import org.scalacheck.{ Arbitrary, ScalacheckShapeless }
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

//noinspection TypeAnnotation
class CombinedShapeEncoderTest
    extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with ScalacheckShapeless {

  import combine.implicits.tuples._
  import combine.syntax._
  import dev.vgerasimov.shapelse.empty.implicits.all._
  import dev.vgerasimov.shapelse.empty.instances._
  import names.implicits.all._
  import typenames.implicits.all._
  import values.implicits.all._

  test("Combining of name and type name encoders providing product shape with primitives only should be successful") {
    case class C(f: Float, c: Char, l: Long)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[C].combine(typeNamesShapeEncoder[C])
    val expected =
      ProductShape(
        ("", "C"),
        List(
          FloatShape("f", "Float"),
          CharShape("c", "Char"),
          LongShape("l", "Long")
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Combining of name and type name encoders providing product shape should be successful") {
    case class C1(f: Float, c: Char, l: Long)
    case class C(s: String, inner: C1, d: Double)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[C].combine(typeNamesShapeEncoder[C])
    val expected =
      ProductShape(
        ("", "C"),
        List(
          StringShape("s", "String"),
          ProductShape(
            ("inner", "C1"),
            List(
              FloatShape("f", "Float"),
              CharShape("c", "Char"),
              LongShape("l", "Long")
            )
          ),
          DoubleShape("d", "Double")
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: C) => encoder.encode(a) shouldBe expected }
  }

  test("Combining of name and type name encoders providing list product shape should be successful") {
    case class C1(f: Float, c: Char, l: Long)
    case class C(s: String, inner: C1, d: Double)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = namesShapeEncoder[List[C]].combine(typeNamesShapeEncoder[List[C]])
    val expected =
      ListShape(
        ("", "List[C]"),
        List(
          ProductShape(
            ("", "C"),
            List(
              StringShape("s", "String"),
              ProductShape(
                ("inner", "C1"),
                List(
                  FloatShape("f", "Float"),
                  CharShape("c", "Char"),
                  LongShape("l", "Long")
                )
              ),
              DoubleShape("d", "Double")
            )
          )
        )
      )
    encoder.encode shouldBe expected
    forAll { (a: List[C]) => encoder.encode(a) shouldBe expected }
  }

  test("Combining of value and name encoders providing product shape with primitives only should be successful") {
    case class C(f: Float, c: Char, l: Long)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueShapeEncoder[C].combine(namesShapeEncoder[C])
    forAll { (a: C) =>
      encoder.encode(a) shouldBe
      ProductShape(
        (ProductValue, ""),
        List[Shape[(Value, String)]](
          FloatShape(FloatValue(a.f), "f"),
          CharShape(CharValue(a.c), "c"),
          LongShape(LongValue(a.l), "l")
        )
      )
    }
  }

  test(
    """Combining of value and name encoders providing empty list product shape with primitives only should be successful"""
  ) {
    case class C(f: Float, c: Char, l: Long)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueShapeEncoder[List[C]].combine(namesShapeEncoder[List[C]])
    encoder.encode(List[C]()) shouldBe
    ListShape(
      (NilValue, ""),
      List(
        ProductShape(
          (NilValue, ""),
          List[Shape[(Value, String)]](
            FloatShape(NilValue, "f"),
            CharShape(NilValue, "c"),
            LongShape(NilValue, "l")
          )
        )
      )
    )
  }

  test(
    """Combining of value and name encoders providing non-empty list product shape with primitives only should be successful"""
  ) {
    case class C(f: Float, c: Char, l: Long)
    implicit val arbitrary = implicitly[Arbitrary[C]]
    val encoder = valueShapeEncoder[List[C]].combine(namesShapeEncoder[List[C]])
    forAll(Arbitrary.arbitrary[List[C]].suchThat(_.nonEmpty)) { (a: List[C]) =>
      encoder.encode(a) shouldBe
      ListShape(
        (ListValue, ""),
        a.map(aa =>
          ProductShape(
            (ProductValue, ""),
            List[Shape[(Value, String)]](
              FloatShape(FloatValue(aa.f), "f"),
              CharShape(CharValue(aa.c), "c"),
              LongShape(LongValue(aa.l), "l")
            )
          )
        )
      )
    }
  }

  test("Combining of value and name encoders providing coproduct shape should be successful") {
    sealed trait T
    case class C1(f: Float, c: Char, l: Long) extends T
    case class C2(s: String, b: Byte) extends T
    implicit val arbitrary = implicitly[Arbitrary[T]]
    val encoder = valueShapeEncoder[T].combine(namesShapeEncoder[T])
    forAll { (a: C1) =>
      encoder.encode(a) shouldBe
      CoproductShape(
        (CoproductValue, ""),
        List[Shape[(Value, String)]](
          ProductShape(
            (ProductValue, "C1"),
            List(
              FloatShape((FloatValue(a.f), "f")),
              CharShape((CharValue(a.c), "c")),
              LongShape((LongValue(a.l), "l"))
            )
          ),
          ProductShape(
            (NilValue, "C2"),
            List(
              StringShape((NilValue, "s")),
              ByteShape((NilValue, "b"))
            )
          )
        )
      )
    }
    forAll { (a: C2) =>
      encoder.encode(a) shouldBe
      CoproductShape(
        (CoproductValue, ""),
        List[Shape[(Value, String)]](
          ProductShape(
            (NilValue, "C1"),
            List(
              FloatShape((NilValue, "f")),
              CharShape((NilValue, "c")),
              LongShape((NilValue, "l"))
            )
          ),
          ProductShape(
            (ProductValue, "C2"),
            List(
              StringShape((StringValue(a.s), "s")),
              ByteShape((ByteValue(a.b), "b"))
            )
          )
        )
      )
    }
  }
}
