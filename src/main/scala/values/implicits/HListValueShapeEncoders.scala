package dev.vgerasimov.shapelse
package values
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

trait HListValueShapeEncoders {
  import ProductValueShapeEncoder.instance

  implicit val hnilValueShapeEncoder: ProductValueShapeEncoder[HNil] =
    instance((_: HNil) => ProductShape(NilValue, List()))

  implicit def hlistValueShapeEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[ValueShapeEncoder[H]],
    tEncoder: ProductValueShapeEncoder[T]
  ): ProductValueShapeEncoder[FieldType[K, H] :: T] =
    instance((a: FieldType[K, H] :: T) => {
      val childs = hEncoder.value.encode(a.head) :: tEncoder.encode(a.tail).childs
      ProductShape(NilValue, childs)
    })
}
