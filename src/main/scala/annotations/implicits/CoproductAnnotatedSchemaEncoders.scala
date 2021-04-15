package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.{ :+:, CNil, Coproduct, Lazy }

trait CoproductAnnotatedSchemaEncoders {
  import CoproductAnnotatedSchemaEncoder.instance

  implicit def cnilAnnotatedSchemaEncoder[A]: CoproductAnnotatedSchemaEncoder[A, CNil] =
    instance(CoproductSchema(None, List()))

  implicit def coproductAnnotatedSchemaEncoder[A, H, T <: Coproduct](
    implicit
    hEncoder: Lazy[AnnotatedSchemaEncoder[A, H]],
    tEncoder: CoproductAnnotatedSchemaEncoder[A, T]
  ): CoproductAnnotatedSchemaEncoder[A, H :+: T] =
    instance(CoproductSchema(None, hEncoder.value.encode :: tEncoder.encode.childs))
}
