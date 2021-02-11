package dev.vgerasimov.scmc
package implicits

import shapeless.labelled.FieldType
import shapeless.{ :+:, CNil, Coproduct, Lazy, Witness }

import scala.collection.immutable.ListMap

trait CoproductFieldEncoders {

  implicit val cnilFieldEncoder: CoproductFieldEncoder[CNil] =
    CoproductFieldEncoder.instance(CoproductField(ListMap()))

  implicit def coproductFieldEncoder[K <: Symbol, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[FieldEncoder[H]],
    tEncoder: CoproductFieldEncoder[T]
  ): CoproductFieldEncoder[FieldType[K, H] :+: T] = CoproductFieldEncoder.instance[FieldType[K, H] :+: T] {
    val hFields = ListMap[Symbol, Field](witness.value -> hEncoder.value.encode)
    val tFields = tEncoder.encode.childs
    CoproductField(hFields ++ tFields)
  }
}
