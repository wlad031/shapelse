package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.combine.Combiner
import org.scalatest.funsuite.AnyFunSuite
import empty.instances._

class ShapeTest extends AnyFunSuite {

  test("Map on boolean shape should be successful") {
    val shape = BooleanShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === BooleanShape(Some(11)))
  }

  test("Map on char shape should be successful") {
    val shape = CharShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === CharShape(Some(11)))
  }

  test("Map on string shape should be successful") {
    val shape = StringShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === StringShape(Some(11)))
  }

  test("Map on short shape should be successful") {
    val shape = ShortShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === ShortShape(Some(11)))
  }

  test("Map on int shape should be successful") {
    val shape = IntShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === IntShape(Some(11)))
  }

  test("Map on long shape should be successful") {
    val shape = LongShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === LongShape(Some(11)))
  }

  test("Map on float shape should be successful") {
    val shape = FloatShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === FloatShape(Some(11)))
  }

  test("Map on double shape should be successful") {
    val shape = DoubleShape(Some(1))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === DoubleShape(Some(11)))
  }

  test("Map on option shape should be successful") {
    val shape = OptionShape(Some(0), Some(DoubleShape(Some(1))))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === OptionShape(Some(10), Some(DoubleShape(Some(11)))))
  }

  test("Map on list shape should be successful") {
    val shape = ListShape(Some(0), List(DoubleShape(Some(1))))
    val mapped = shape.map(_.map(x => x + 10))
    assert(mapped === ListShape(Some(10), List(DoubleShape(Some(11)))))
  }

  test("Map on product shape containing primitives only should be successful") {
    val shape = ProductShape(
      Some(1),
      List[Shape[Option[Int]]](
        IntShape(Some(10)),
        StringShape(Some(11)),
        BooleanShape(None)
      )
    )
    val mapped = shape.map(_.map(x => x + 10))
    assert(
      mapped === ProductShape(
          Some(11),
          List[Shape[Option[Int]]](
            IntShape(Some(20)),
            StringShape(Some(21)),
            BooleanShape(None)
          )
        )
    )
  }

  test("Combine on product shapes containing primitives only should be successful") {
    val shape1 = ProductShape(
      Some(1),
      List[Shape[Option[Int]]](
        IntShape(Some(10)),
        StringShape(Some(11))
      )
    )
    val shape2 = ProductShape[Option[String]](
      Some("one"),
      List[Shape[Option[String]]](
        IntShape[Option[String]](Some("ten")),
        StringShape[Option[String]](Some("eleven"))
      )
    )
    implicit val makeTuple: Combiner[Option[Int], Option[String], (Option[Int], Option[String])] =
      (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = shape1.combine(shape2)
    assert(
      combined === ProductShape(
          (Some(1), Some("one")),
          List[Shape[(Option[Int], Option[String])]](
            IntShape((Some(10), Some("ten"))),
            StringShape((Some(11), Some("eleven")))
          )
        )
    )
  }

  test("Combine on coproduct shapes should be successful") {
    val shape1 = CoproductShape(
      Some(1),
      List(
        ProductShape[Option[Int]](Some(10), List()),
        ProductShape[Option[Int]](Some(11), List())
      )
    )
    val shape2 = CoproductShape[Option[String]](
      Some("one"),
      List(
        ProductShape[Option[String]](Some("ten"), List()),
        ProductShape[Option[String]](Some("eleven"), List())
      )
    )
    implicit val makeTuple: Combiner[Option[Int], Option[String], (Option[Int], Option[String])] =
      (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = shape1.combine(shape2)
    assert(
      combined === CoproductShape(
          (Some(1), Some("one")),
          List(
            ProductShape((Some(10), Some("ten")), List()),
            ProductShape((Some(11), Some("eleven")), List())
          )
        )
    )
  }
}
