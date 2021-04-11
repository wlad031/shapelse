package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ ::, HList, HNil, Lazy }

trait HListTypeNameSchemaEncoders {
  import ProductTypeNameSchemaEncoder.instance

  implicit val hnilTypeNameSchemaEncoder: ProductTypeNameSchemaEncoder[HNil] = instance(ProductSchema("", List()))

  implicit def hlistTypeNameSchemaEncoder[H, T <: HList](
    implicit
    hEncoder: Lazy[TypeNameSchemaEncoder[H]],
    tEncoder: ProductTypeNameSchemaEncoder[T]
  ): ProductTypeNameSchemaEncoder[H :: T] =
    instance(ProductSchema("", hEncoder.value.encode :: tEncoder.encode.childs))
}
