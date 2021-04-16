package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ ::, HList, HNil, Lazy }

trait HListTypeNameShapeEncoders {
  import ProductTypeNameShapeEncoder.instance

  implicit val hnilTypeNameShapeEncoder: ProductTypeNameShapeEncoder[HNil] = instance(ProductShape("", List()))

  implicit def hlistTypeNameShapeEncoder[H, T <: HList](
    implicit
    hEncoder: Lazy[TypeNameShapeEncoder[H]],
    tEncoder: ProductTypeNameShapeEncoder[T]
  ): ProductTypeNameShapeEncoder[H :: T] =
    instance(ProductShape("", hEncoder.value.encode :: tEncoder.encode.childs))
}
