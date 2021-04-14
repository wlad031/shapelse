package dev.vgerasimov.shapelse
package values
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

trait HListValueSchemaEncoders {
  import ProductValueSchemaEncoder.instance

  implicit val hnilValueSchemaEncoder: ProductValueSchemaEncoder[HNil] =
    instance((_: HNil) => ProductSchema(NilValue, List()))

  implicit def hlistValueSchemaEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[ValueSchemaEncoder[H]],
    tEncoder: ProductValueSchemaEncoder[T]
  ): ProductValueSchemaEncoder[FieldType[K, H] :: T] =
    instance((a: FieldType[K, H] :: T) => {
      val childs = hEncoder.value.encode(a.head) :: tEncoder.encode(a.tail).childs
      ProductSchema(NilValue, childs)
    })
}
