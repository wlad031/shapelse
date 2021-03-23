package dev.vgerasimov.shapelse
package structure
package implicits

import empty.Emptible

import shapeless.labelled.FieldType
import shapeless.{ :+:, CNil, Coproduct, Lazy, Witness }

import scala.collection.immutable.ListMap

/** Contains implicits for [[Schema]] derivation for shapeless' Coproduct types. */
trait CoproductSchemaEncoders {

  implicit def cnilSchemaEncoder[M : Emptible]: CoproductSchemaEncoder[M, CNil] =
    CoproductSchemaEncoder.instance(CoproductSchema(Emptible.summon.empty, ListMap()))

  implicit def coproductSchemaEncoder[M : Emptible, K <: Symbol, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[SchemaEncoder[M, H]],
    tEncoder: CoproductSchemaEncoder[M, T]
  ): CoproductSchemaEncoder[M, FieldType[K, H] :+: T] = CoproductSchemaEncoder.instance {
    val hSchemas = Map[Symbol, Schema[M]](witness.value -> hEncoder.value.encode)
    val tSchemas = tEncoder.encode.childs
    CoproductSchema(Emptible.summon.empty, hSchemas ++ tSchemas)
  }
}
