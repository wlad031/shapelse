package dev.vgerasimov.shapelse
package values
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.Values
import shapeless.{ <:!<, =:!=, Coproduct, HList, LabelledGeneric, Lazy }

import scala.List

trait GenericValueShapeEncoders {

  implicit def genericListValueShapeEncoder[T](
    implicit
    tEncoder: ValueShapeEncoder[T]
  ): ValueShapeEncoder[List[T]] = ValueShapeEncoder.instance { (ls: List[T]) =>
    {
      val value = ls.map(tEncoder.encode)
      val value2 = value.map(_.meta)
      val value1 = ListValue
      ListShape(value1, value)
    }
  }

  implicit def genericProductValueShapeEncoder[T, VRepr <: HList, TRepr <: HList](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: Lazy[ProductValueShapeEncoder[TRepr]],
    values: Values.Aux[TRepr, VRepr],
    toList: ToTraversable.Aux[VRepr, List, Any],
    notList: T <:!< List[_]
  ): ProductValueShapeEncoder[T] = ProductValueShapeEncoder.instance { (a: T) =>
    {
      val tRepr = gen.to(a)
      val repr = reprEncoder.value.encode(tRepr)
      val childs =
        (repr.childs zip values(tRepr).toList
          .map({
            case x: Boolean => BooleanValue(x)
            case x: Char    => CharValue(x)
            case x: String  => StringValue(x)
            case x: Byte    => ByteValue(x)
            case x: Short   => ShortValue(x)
            case x: Int     => IntValue(x)
            case x: Long    => LongValue(x)
            case x: Float   => FloatValue(x)
            case x: Double  => DoubleValue(x)
            case _          => NilValue
          })
          .map(x => x: Value))
          .map({
            case (shape, value) =>
              shape.map(shapeValue =>
                value match {
                  case NilValue   => shapeValue
                  case otherValue => otherValue
                }
              )
          })

      ProductShape(ProductValue, childs)
    }
  }

  implicit def genericCoproductValueShapeEncoder[T, VRepr <: HList, TRepr <: Coproduct](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: Lazy[CoproductValueShapeEncoder[TRepr]],
    notList: T <:!< List[_]
  ): CoproductValueShapeEncoder[T] = CoproductValueShapeEncoder.instance { (a: T) =>
    {
      val tRepr = gen.to(a)
      val repr = reprEncoder.value.encode(tRepr)
      CoproductShape(CoproductValue, repr.childs)
    }
  }
}
