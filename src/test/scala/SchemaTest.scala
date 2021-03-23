package dev.vgerasimov.shapelse

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

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
    val schema = OptionSchema(Some(0), DoubleSchema(Some(1)))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === OptionSchema(Some(10), DoubleSchema(Some(11))))
  }

  test("Map on list schema should be successful") {
    val schema = ListSchema(Some(0), DoubleSchema(Some(1)))
    val mapped = schema.map(_.map(x => x + 10))
    assert(mapped === ListSchema(Some(10), DoubleSchema(Some(11))))
  }

  test("Map on product schema containing primitives only should be successful") {
    val schema = ProductSchema(
      Some(1),
      ListMap[Symbol, Schema[Option[Int]]](
        Symbol("a") -> IntSchema(Some(10)),
        Symbol("b") -> StringSchema(Some(11)),
        Symbol("c") -> BooleanSchema(None)
      )
    )
    val mapped = schema.map(_.map(x => x + 10))
    assert(
      mapped === ProductSchema(
          Some(11),
          ListMap[Symbol, Schema[Option[Int]]](
            Symbol("a") -> IntSchema(Some(20)),
            Symbol("b") -> StringSchema(Some(21)),
            Symbol("c") -> BooleanSchema(None)
          )
        )
    )
  }

  test("Combine on product schemas containing primitives only should be successful") {
    val schema1 = ProductSchema(
      Some(1),
      ListMap(
        Symbol("a") -> IntSchema(Some(10)),
        Symbol("b") -> StringSchema(Some(11))
      )
    )
    val schema2 = ProductSchema[Option[String]](
      Some("one"),
      ListMap(
        Symbol("a") -> IntSchema[Option[String]](Some("ten")),
        Symbol("b") -> StringSchema[Option[String]](Some("eleven"))
      )
    )
    val makeTuple = (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = schema1.combine(makeTuple)(schema2)
    assert(
      combined === ProductSchema(
          (Some(1), Some("one")),
          ListMap[Symbol, Schema[(Option[Int], Option[String])]](
            Symbol("a") -> IntSchema((Some(10), Some("ten"))),
            Symbol("b") -> StringSchema((Some(11), Some("eleven")))
          )
        )
    )
  }

  test("Combine on coproduct schemas should be successful") {
    val schema1 = CoproductSchema(
      Some(1),
      Map(
        Symbol("a") -> ProductSchema(Some(10), ListMap()),
        Symbol("b") -> ProductSchema(Some(11), ListMap())
      )
    )
    val schema2 = CoproductSchema[Option[String]](
      Some("one"),
      Map(
        Symbol("a") -> ProductSchema[Option[String]](Some("ten"), ListMap()),
        Symbol("b") -> ProductSchema[Option[String]](Some("eleven"), ListMap())
      )
    )
    val makeTuple = (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = schema1.combine(makeTuple)(schema2)
    assert(
      combined === CoproductSchema(
          (Some(1), Some("one")),
          Map[Symbol, Schema[(Option[Int], Option[String])]](
            Symbol("a") -> ProductSchema((Some(10), Some("ten")), ListMap()),
            Symbol("b") -> ProductSchema((Some(11), Some("eleven")), ListMap())
          )
        )
    )
  }
}
