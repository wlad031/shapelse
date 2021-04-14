package dev.vgerasimov.shapelse
package values
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.ops.record.Values
import shapeless.{ <:!<, =:!=, Coproduct, HList, LabelledGeneric, Lazy }

import scala.List

trait GenericValueSchemaEncoders {

  implicit def genericListValueSchemaEncoder[T](
    implicit
    tEncoder: ValueSchemaEncoder[T]
  ): ValueSchemaEncoder[List[T]] = ValueSchemaEncoder.instance { (ls: List[T]) =>
    {
      val value = ls.map(tEncoder.encode)
      val value2 = value.map(_.meta)
      val value1 = ListValue
      ListSchema(value1, value)
    }
  }

  implicit def genericProductValueSchemaEncoder[T, VRepr <: HList, TRepr <: HList](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: Lazy[ProductValueSchemaEncoder[TRepr]],
    values: Values.Aux[TRepr, VRepr],
    toList: ToTraversable.Aux[VRepr, List, Any],
    notList: T <:!< List[_]
  ): ProductValueSchemaEncoder[T] = ProductValueSchemaEncoder.instance { (a: T) =>
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
            case (schema, value) =>
              schema.map(schemaValue =>
                value match {
                  case NilValue   => schemaValue
                  case otherValue => otherValue
                }
              )
          })

      ProductSchema(ProductValue, childs)
    }
  }

  implicit def genericCoproductValueSchemaEncoder[T, VRepr <: HList, TRepr <: Coproduct](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: Lazy[CoproductValueSchemaEncoder[TRepr]],
    notList: T <:!< List[_]
  ): CoproductValueSchemaEncoder[T] = CoproductValueSchemaEncoder.instance { (a: T) =>
    {
      val tRepr = gen.to(a)
      val repr = reprEncoder.value.encode(tRepr)
      CoproductSchema(CoproductValue, repr.childs)
    }
  }
}
