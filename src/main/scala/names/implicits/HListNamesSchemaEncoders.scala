package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

/** Contains implicits for [[Schema]] derivation for shapeless' HList types. */
trait HListNamesSchemaEncoders {
  import ProductNameSchemaEncoder.instance

  implicit val hnilNameSchemaEncoder: ProductNameSchemaEncoder[HNil] = instance[HNil](ProductSchema("", List()))

  implicit def hlistNameSchemaEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[NameSchemaEncoder[H]],
    tEncoder: ProductNameSchemaEncoder[T]
  ): ProductNameSchemaEncoder[FieldType[K, H] :: T] =
    instance(ProductSchema("", hEncoder.value.encode.mapMetaOnly(_ => witness.value.name) :: tEncoder.encode.childs))
}
