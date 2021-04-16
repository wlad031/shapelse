package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ :+:, CNil, Coproduct, Lazy }

trait CoproductTypeNameShapeEncoders {
  import CoproductTypeNameShapeEncoder.instance

  implicit val cnilTypeNameShapeEncoder: CoproductTypeNameShapeEncoder[CNil] = instance(CoproductShape("", List()))

  implicit def coproductTypeNameShapeEncoder[H, T <: Coproduct](
    implicit
    hEncoder: Lazy[TypeNameShapeEncoder[H]],
    tEncoder: CoproductTypeNameShapeEncoder[T]
  ): CoproductTypeNameShapeEncoder[H :+: T] =
    instance(CoproductShape("", hEncoder.value.encode :: tEncoder.encode.childs))
}
