package dev.vgerasimov.shapelse
package structure
package implicits

import shapeless.labelled.FieldType
import shapeless.{ :+:, CNil, Coproduct, Lazy, Witness }

import scala.collection.immutable.ListMap

/** Contains implicits for [[Field]] derivation for shapeless' Coproduct types. */
trait CoproductFieldEncoders {

  implicit def cnilFieldEncoder[M : Emptible]: CoproductFieldEncoder[M, CNil] =
    CoproductFieldEncoder.instance(CoproductField(Emptible.summon.empty, ListMap()))

  implicit def coproductFieldEncoder[M : Emptible, K <: Symbol, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[FieldEncoder[M, H]],
    tEncoder: CoproductFieldEncoder[M, T]
  ): CoproductFieldEncoder[M, FieldType[K, H] :+: T] = CoproductFieldEncoder.instance {
    val hFields = ListMap[Symbol, Field[M]](witness.value -> hEncoder.value.encode)
    val tFields = tEncoder.encode.childs
    CoproductField(Emptible.summon.empty, hFields ++ tFields)
  }
}
