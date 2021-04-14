package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.combine.Combiner
import org.scalatest.funsuite.AnyFunSuite
import empty.instances._

class SchemaTest extends AnyFunSuite {

  test("Map on boolean schema should be successful") {
    val schema = BooleanSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === BooleanSchema(Some(11)))
  }

  test("Map on char schema should be successful") {
    val schema = CharSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === CharSchema(Some(11)))
  }

  test("Map on string schema should be successful") {
    val schema = StringSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === StringSchema(Some(11)))
  }

  test("Map on short schema should be successful") {
    val schema = ShortSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === ShortSchema(Some(11)))
  }

  test("Map on int schema should be successful") {
    val schema = IntSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === IntSchema(Some(11)))
  }

  test("Map on long schema should be successful") {
    val schema = LongSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === LongSchema(Some(11)))
  }

  test("Map on float schema should be successful") {
    val schema = FloatSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === FloatSchema(Some(11)))
  }

  test("Map on double schema should be successful") {
    val schema = DoubleSchema(Some(1))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === DoubleSchema(Some(11)))
  }

  test("Map on option schema should be successful") {
    val schema = OptionSchema(Some(0), Some(DoubleSchema(Some(1))))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === OptionSchema(Some(10), Some(DoubleSchema(Some(11)))))
  }

  test("Map on list schema should be successful") {
    val schema = ListSchema(Some(0), List(DoubleSchema(Some(1))))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === ListSchema(Some(10), List(DoubleSchema(Some(11)))))
  }

  test("Map on product schema containing primitives only should be successful") {
    val schema = ProductSchema(
      Some(1),
      List[Schema[Option[Int]]](
        IntSchema(Some(10)),
        StringSchema(Some(11)),
        BooleanSchema(None)
      )
    )
    val mapped = schema.map(_.map(x => x + 10))
    assert(
      mapped === ProductSchema(
          Some(11),
          List[Schema[Option[Int]]](
            IntSchema(Some(20)),
            StringSchema(Some(21)),
            BooleanSchema(None)
          )
        )
    )
  }

  test("Combine on product schemas containing primitives only should be successful") {
    val schema1 = ProductSchema(
      Some(1),
      List[Schema[Option[Int]]](
        IntSchema(Some(10)),
        StringSchema(Some(11))
      )
    )
    val schema2 = ProductSchema[Option[String]](
      Some("one"),
      List[Schema[Option[String]]](
        IntSchema[Option[String]](Some("ten")),
        StringSchema[Option[String]](Some("eleven"))
      )
    )
    implicit val makeTuple: Combiner[Option[Int], Option[String], (Option[Int], Option[String])] =
      (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = schema1.combine(schema2)
    assert(
      combined === ProductSchema(
          (Some(1), Some("one")),
          List[Schema[(Option[Int], Option[String])]](
            IntSchema((Some(10), Some("ten"))),
            StringSchema((Some(11), Some("eleven")))
          )
        )
    )
  }

  test("Combine on coproduct schemas should be successful") {
    val schema1 = CoproductSchema(
      Some(1),
      List(
        ProductSchema[Option[Int]](Some(10), List()),
        ProductSchema[Option[Int]](Some(11), List())
      )
    )
    val schema2 = CoproductSchema[Option[String]](
      Some("one"),
      List(
        ProductSchema[Option[String]](Some("ten"), List()),
        ProductSchema[Option[String]](Some("eleven"), List())
      )
    )
    implicit val makeTuple: Combiner[Option[Int], Option[String], (Option[Int], Option[String])] =
      (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = schema1.combine(schema2)
    assert(
      combined === CoproductSchema(
          (Some(1), Some("one")),
          List(
            ProductSchema((Some(10), Some("ten")), List()),
            ProductSchema((Some(11), Some("eleven")), List())
          )
        )
    )
  }
}
