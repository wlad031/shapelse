package dev.vgerasimov.shapelse

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.immutable.ListMap

class FieldTest extends AnyFunSuite {

  test("Map on boolean field should be successful") {
    val field = BooleanField(Some(1))
    val mapped = field.map(_.map(x => x + 10))
    assert(mapped === BooleanField(Some(11)))
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
}
