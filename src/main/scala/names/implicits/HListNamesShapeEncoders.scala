package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

/** Contains implicits for [[Shape]] derivation for shapeless' HList types. */
trait HListNamesShapeEncoders {
  import ProductNameShapeEncoder.instance

  implicit val hnilNameShapeEncoder: ProductNameShapeEncoder[HNil] = instance[HNil](ProductShape("", List()))

  implicit def hlistNameShapeEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[NameShapeEncoder[H]],
    tEncoder: ProductNameShapeEncoder[T]
  ): ProductNameShapeEncoder[FieldType[K, H] :: T] =
    instance(ProductShape("", hEncoder.value.encode.mapMetaOnly(_ => witness.value.name) :: tEncoder.encode.childs))
}
