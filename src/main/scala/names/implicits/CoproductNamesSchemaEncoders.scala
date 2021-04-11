package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.labelled.FieldType
import shapeless.{ :+:, CNil, Coproduct, Lazy, Witness }

/** Contains implicits for [[Schema]] derivation for shapeless' Coproduct types. */
trait CoproductNamesSchemaEncoders {
  import CoproductNameSchemaEncoder.instance

  implicit val cnilNameSchemaEncoder: CoproductNameSchemaEncoder[CNil] = instance(CoproductSchema("", List()))

  implicit def coproductNameSchemaEncoder[K <: Symbol, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[NameSchemaEncoder[H]],
    tEncoder: CoproductNameSchemaEncoder[T]
  ): CoproductNameSchemaEncoder[FieldType[K, H] :+: T] =
    instance(CoproductSchema("", hEncoder.value.encode.mapMetaOnly(_ => witness.value.name) :: tEncoder.encode.childs))
}
