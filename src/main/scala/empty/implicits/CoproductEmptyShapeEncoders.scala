package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ :+:, CNil, Coproduct, Lazy }

trait CoproductEmptyShapeEncoders {
  import CoproductShapeEncoder.instance

  implicit val cnilEmptyShapeEncoder: CoproductShapeEncoder[Empty.type, CNil] =
    instance(CoproductShape(Empty, List()))

  implicit def coproductEmptyShapeEncoder[H, T <: Coproduct](
    implicit
    hEncoder: Lazy[ShapeEncoder[Empty.type, H]],
    tEncoder: CoproductShapeEncoder[Empty.type, T]
  ): CoproductShapeEncoder[Empty.type, H :+: T] =
    instance(CoproductShape(Empty, hEncoder.value.encode :: tEncoder.encode.childs))
}
