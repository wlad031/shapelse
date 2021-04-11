package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ :+:, CNil, Coproduct, Lazy }

trait CoproductTypeNameSchemaEncoders {
  import CoproductTypeNameSchemaEncoder.instance

  implicit val cnilTypeNameSchemaEncoder: CoproductTypeNameSchemaEncoder[CNil] = instance(CoproductSchema("", List()))

  implicit def coproductTypeNameSchemaEncoder[H, T <: Coproduct](
    implicit
    hEncoder: Lazy[TypeNameSchemaEncoder[H]],
    tEncoder: CoproductTypeNameSchemaEncoder[T]
  ): CoproductTypeNameSchemaEncoder[H :+: T] =
    instance(CoproductSchema("", hEncoder.value.encode :: tEncoder.encode.childs))
}
