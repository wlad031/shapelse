package dev.vgerasimov.shapelse

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class FieldTest extends AnyFunSuite {

  test("Map on boolean field should be successful") {
    val field = BooleanField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === BooleanField(Some(11)))
  }

  test("Map on char field should be successful") {
    val field = CharField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === CharField(Some(11)))
  }

  test("Map on string field should be successful") {
    val field = StringField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === StringField(Some(11)))
  }

  test("Map on short field should be successful") {
    val field = ShortField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === ShortField(Some(11)))
  }

  test("Map on int field should be successful") {
    val field = IntField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === IntField(Some(11)))
  }

  test("Map on long field should be successful") {
    val field = LongField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === LongField(Some(11)))
  }

  test("Map on float field should be successful") {
    val field = FloatField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === FloatField(Some(11)))
  }

  test("Map on double field should be successful") {
    val field = DoubleField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === DoubleField(Some(11)))
  }

  test("Map on option field should be successful") {
    val field = OptionField(Some(0), DoubleField(Some(1)))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === OptionField(Some(10), DoubleField(Some(11))))
  }

  test("Map on list field should be successful") {
    val field = ListField(Some(0), DoubleField(Some(1)))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === ListField(Some(10), DoubleField(Some(11))))
  }

  test("Map on product field containing primitives only should be successful") {
    val field = ProductField(
      Some(1),
      ListMap[Symbol, Field[Option[Int]]](
        Symbol("a") -> IntField(Some(10)),
        Symbol("b") -> StringField(Some(11)),
        Symbol("c") -> BooleanField(None)
      )
    )
    val mapped = field.map(_.map(x => x + 10))
    assert(
      mapped === ProductField(
          Some(11),
          ListMap[Symbol, Field[Option[Int]]](
            Symbol("a") -> IntField(Some(20)),
            Symbol("b") -> StringField(Some(21)),
            Symbol("c") -> BooleanField(None)
          )
        )
    )
  }

  test("Combine on product fields containing primitives only should be successful") {
    val field1 = ProductField(
      Some(1),
      ListMap(
        Symbol("a") -> IntField(Some(10)),
        Symbol("b") -> StringField(Some(11))
      )
    )
    val field2 = ProductField[Option[String]](
      Some("one"),
      ListMap(
        Symbol("a") -> IntField[Option[String]](Some("ten")),
        Symbol("b") -> StringField[Option[String]](Some("eleven"))
      )
    )
    val makeTuple = (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = field1.combine(makeTuple)(field2)
    assert(
      combined === ProductField(
          (Some(1), Some("one")),
          ListMap[Symbol, Field[(Option[Int], Option[String])]](
            Symbol("a") -> IntField((Some(10), Some("ten"))),
            Symbol("b") -> StringField((Some(11), Some("eleven")))
          )
        )
    )
  }

  test("Combine on coproduct fields should be successful") {
    val field1 = CoproductField(
      Some(1),
      Map(
        Symbol("a") -> ProductField(Some(10), ListMap()),
        Symbol("b") -> ProductField(Some(11), ListMap())
      )
    )
    val field2 = CoproductField[Option[String]](
      Some("one"),
      Map(
        Symbol("a") -> ProductField[Option[String]](Some("ten"), ListMap()),
        Symbol("b") -> ProductField[Option[String]](Some("eleven"), ListMap())
      )
    )
    val makeTuple = (o1: Option[Int], o2: Option[String]) => (o1, o2)
    val combined = field1.combine(makeTuple)(field2)
    assert(
      combined === CoproductField(
          (Some(1), Some("one")),
          Map[Symbol, Field[(Option[Int], Option[String])]](
            Symbol("a") -> ProductField((Some(10), Some("ten")), ListMap()),
            Symbol("b") -> ProductField((Some(11), Some("eleven")), ListMap())
          )
        )
    )
  }
}
