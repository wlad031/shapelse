package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ ::, HList, HNil, Lazy }

trait HListEmptyShapeEncoders {
  import ProductShapeEncoder.instance

  implicit val hnilEmptyShapeEncoder: ProductShapeEncoder[Empty.type, HNil] =
    instance[Empty.type, HNil](ProductShape(Empty, List()))

  implicit def hlistEmptyShapeEncoder[H, T <: HList](
    implicit
    hEncoder: Lazy[ShapeEncoder[Empty.type, H]],
    tEncoder: ProductShapeEncoder[Empty.type, T]
  ): ProductShapeEncoder[Empty.type, H :: T] =
    instance(ProductShape(Empty, hEncoder.value.encode :: tEncoder.encode.childs))
}
