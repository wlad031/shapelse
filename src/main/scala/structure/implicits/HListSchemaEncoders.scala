package dev.vgerasimov.shapelse
package structure
package implicits

import empty.Emptible

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, Lazy, Witness }

import scala.collection.immutable.ListMap

/** Contains implicits for [[Schema]] derivation for shapeless' HList types. */
trait HListSchemaEncoders {

  implicit def hnilSchemaEncoder[M : Emptible]: ProductSchemaEncoder[M, HNil] =
    ProductSchemaEncoder.instance[M, HNil](ProductSchema(Emptible.summon.empty, ListMap()))

  implicit def hlistSchemaEncoder[M : Emptible, K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[SchemaEncoder[M, H]],
    tEncoder: ProductSchemaEncoder[M, T]
  ): ProductSchemaEncoder[M, FieldType[K, H] :: T] = ProductSchemaEncoder.instance {
    val hSchemas = ListMap[Symbol, Schema[M]](witness.value -> hEncoder.value.encode)
    val tSchemas = tEncoder.encode.childs
    ProductSchema(Emptible.summon.empty, hSchemas ++ tSchemas)
  }
}
