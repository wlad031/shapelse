package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.{ ::, HList, HNil, Lazy }

trait HListAnnotatedShapeEncoders {
  import ProductAnnotatedShapeEncoder.instance

  implicit def hnilAnnotatedShapeEncoder[A]: ProductAnnotatedShapeEncoder[A, HNil] =
    instance[A, HNil](ProductShape(None, List()))

  implicit def hlistAnnotatedShapeEncoder[A, H, T <: HList](
    implicit
    hEncoder: Lazy[AnnotatedShapeEncoder[A, H]],
    tEncoder: ProductAnnotatedShapeEncoder[A, T]
  ): ProductAnnotatedShapeEncoder[A, H :: T] =
    instance(ProductShape(None, hEncoder.value.encode :: tEncoder.encode.childs))
}
