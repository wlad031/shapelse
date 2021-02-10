package dev.vgerasimov.scmc
package implicits

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

import scala.collection.immutable.ListMap

trait HListFieldEncoders {

  implicit val hnilFieldEncoder: ObjectFieldEncoder[HNil] =
    ObjectFieldEncoder.instance[HNil](ObjectField(ListMap()))

  implicit def hlistFieldEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[FieldEncoder[H]],
    tEncoder: ObjectFieldEncoder[T]
  ): ObjectFieldEncoder[FieldType[K, H] :: T] = ObjectFieldEncoder.instance {
    val hFields = ListMap[Symbol, Field](witness.value -> hEncoder.value.encode)
    val tFields = tEncoder.encode.fields
    ObjectField(hFields ++ tFields)
  }
}
