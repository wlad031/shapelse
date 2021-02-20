package dev.vgerasimov.shapelse
package structure
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

import scala.collection.immutable.ListMap

/** Contains implicits for [[Field]] derivation for shapeless' HList types. */
trait HListFieldEncoders {

  implicit def hnilFieldEncoder[M : Emptible]: ProductFieldEncoder[M, HNil] =
    ProductFieldEncoder.instance[M, HNil](ProductField(Emptible.summon.empty, ListMap()))

  implicit def hlistFieldEncoder[M : Emptible, K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[FieldEncoder[M, H]],
    tEncoder: ProductFieldEncoder[M, T]
  ): ProductFieldEncoder[M, FieldType[K, H] :: T] = ProductFieldEncoder.instance {
    val hFields = ListMap[Symbol, Field[M]](witness.value -> hEncoder.value.encode)
    val tFields = tEncoder.encode.childs
    ProductField(Emptible.summon.empty, hFields ++ tFields)
  }
}
