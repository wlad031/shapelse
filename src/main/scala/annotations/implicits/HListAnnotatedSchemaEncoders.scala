package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.{ ::, HList, HNil, Lazy }

trait HListAnnotatedSchemaEncoders {
  import ProductAnnotatedSchemaEncoder.instance

  implicit def hnilAnnotatedSchemaEncoder[A]: ProductAnnotatedSchemaEncoder[A, HNil] =
    instance[A, HNil](ProductSchema(None, List()))

  implicit def hlistAnnotatedSchemaEncoder[A, H, T <: HList](
    implicit
    hEncoder: Lazy[AnnotatedSchemaEncoder[A, H]],
    tEncoder: ProductAnnotatedSchemaEncoder[A, T]
  ): ProductAnnotatedSchemaEncoder[A, H :: T] =
    instance(ProductSchema(None, hEncoder.value.encode :: tEncoder.encode.childs))
}
