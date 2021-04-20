package dev.vgerasimov.shapelse

import dev.vgerasimov.shapelse.empty.Empty
import shapeless.Lazy

object Main {

  def main(args: Array[String]): Unit = {
    import combine.implicits.all._
    import empty.implicits.all._
    import empty.instances._
    import names.implicits.all._
    import typenames.implicits.all._
    import values.implicits.all._

    sealed trait T
    sealed trait T1 extends T
    final case class E(e: Int) extends T
    final case class E1(ls: List[E]) extends T
    final case class AB(x: Int, y: String) extends T
    final case class A(floatF: Float, abF: AB, ab2F: AB, booleanF: Boolean) extends T1

    final case class C(t1: T, t2: T, t3: T)

    val ab = AB(10, "hello")
    val ab2 = AB(20, "world")
    val a = A(100.12f, ab, ab2, true)

    type Type = List[T]

    val valueEncoder = valueShapeEncoder[Type]
    val nameEncoder = namesShapeEncoder[Type]
    val typeNameEncoder = typeNamesShapeEncoder[Type]

    val x = valueEncoder.combine(typeNameEncoder).encode(List(E(1), E(2)))

    implicit val prettyEnc: ShapeInstanceEncoder[Prettifier.Data, Type] = valueEncoder
      .combine(nameEncoder)
      .combine(typeNameEncoder)
      .map({ case (v, s, s1) => Prettifier.Data(v, s, s1) })

    implicit val prettifierConfig: Prettifier.Config = Prettifier.Config(
      modifiers = Prettifier.Modifiers(
        value = s => fansi.Str(s).overlay(fansi.Color.Yellow),
        fieldName = s => fansi.Str(s).overlay(fansi.Color.Green),
        typeName = s => fansi.Str(s).overlay(fansi.Bold.On ++ fansi.Color.LightGreen),
        chars = s => fansi.Str(s).overlay(fansi.Color.DarkGray)
      )
    )
    val prettifier = Prettifier.instance[Type]

    println(prettifier.prettify(List(E1(List(E(10), E(20))), a, E(1), ab, E(2), ab2, E(3), a, ab)))
//    println(prettifier.prettify(List(E(1), E(2))))
//    println(prettifier.render(List(3)))

    val intList = List(10, 20)
    val listValueEncoder = valueShapeEncoder[List[Int]]
    val listTypeNameEncoder = typeNamesShapeEncoder[List[Int]]
    val listValues = listValueEncoder.encode(intList)
    val listTypeNames = listTypeNameEncoder.encode
    val listCombines = listTypeNameEncoder.combine(listValueEncoder).encode(intList)
    println(listValues)
    println(listTypeNames)
    println(listCombines)
  }
}
