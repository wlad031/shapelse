package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.labelled.FieldType
import shapeless.{ :+:, CNil, Coproduct, Lazy, Witness }

/** Contains implicits for [[Shape]] derivation for shapeless' Coproduct types. */
trait CoproductNamesShapeEncoders {
  import CoproductNameShapeEncoder.instance

  implicit val cnilNameShapeEncoder: CoproductNameShapeEncoder[CNil] = instance(CoproductShape("", List()))

  implicit def coproductNameShapeEncoder[K <: Symbol, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[NameShapeEncoder[H]],
    tEncoder: CoproductNameShapeEncoder[T]
  ): CoproductNameShapeEncoder[FieldType[K, H] :+: T] =
    instance(CoproductShape("", hEncoder.value.encode.mapMetaOnly(_ => witness.value.name) :: tEncoder.encode.childs))
}
