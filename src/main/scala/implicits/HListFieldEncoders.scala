package dev.vgerasimov.scmc
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

import scala.collection.immutable.ListMap

/** Contains implicits for [[Field]] derivation for shapeless' [[HList]] types. */
trait HListFieldEncoders {

  implicit val hnilFieldEncoder: ProductFieldEncoder[HNil] =
    ProductFieldEncoder.instance[HNil](ProductField(ListMap()))

  implicit def hlistFieldEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[FieldEncoder[H]],
    tEncoder: ProductFieldEncoder[T]
  ): ProductFieldEncoder[FieldType[K, H] :: T] = ProductFieldEncoder.instance {
    val hFields = ListMap[Symbol, Field](witness.value -> hEncoder.value.encode)
    val tFields = tEncoder.encode.childs
    ProductField(hFields ++ tFields)
  }
}
