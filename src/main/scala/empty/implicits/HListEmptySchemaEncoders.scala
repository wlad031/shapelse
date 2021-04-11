package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ ::, HList, HNil, Lazy }

trait HListEmptySchemaEncoders {
  import ProductSchemaEncoder.instance

  implicit val hnilEmptySchemaEncoder: ProductSchemaEncoder[Empty.type, HNil] =
    instance[Empty.type, HNil](ProductSchema(Empty, List()))

  implicit def hlistEmptySchemaEncoder[H, T <: HList](
    implicit
    hEncoder: Lazy[SchemaEncoder[Empty.type, H]],
    tEncoder: ProductSchemaEncoder[Empty.type, T]
  ): ProductSchemaEncoder[Empty.type, H :: T] =
    instance(ProductSchema(Empty, hEncoder.value.encode :: tEncoder.encode.childs))
}
