package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.{ :+:, CNil, Coproduct, Lazy }

trait CoproductAnnotatedShapeEncoders {
  import CoproductAnnotatedShapeEncoder.instance

  implicit def cnilAnnotatedShapeEncoder[A]: CoproductAnnotatedShapeEncoder[A, CNil] =
    instance(CoproductShape(None, List()))

  implicit def coproductAnnotatedShapeEncoder[A, H, T <: Coproduct](
    implicit
    hEncoder: Lazy[AnnotatedShapeEncoder[A, H]],
    tEncoder: CoproductAnnotatedShapeEncoder[A, T]
  ): CoproductAnnotatedShapeEncoder[A, H :+: T] =
    instance(CoproductShape(None, hEncoder.value.encode :: tEncoder.encode.childs))
}
