package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ :+:, CNil, Coproduct, Lazy }

trait CoproductEmptySchemaEncoders {
  import CoproductSchemaEncoder.instance

  implicit val cnilEmptySchemaEncoder: CoproductSchemaEncoder[Empty.type, CNil] =
    instance(CoproductSchema(Empty, List()))

  implicit def coproductEmptySchemaEncoder[H, T <: Coproduct](
    implicit
    hEncoder: Lazy[SchemaEncoder[Empty.type, H]],
    tEncoder: CoproductSchemaEncoder[Empty.type, T]
  ): CoproductSchemaEncoder[Empty.type, H :+: T] =
    instance(CoproductSchema(Empty, hEncoder.value.encode :: tEncoder.encode.childs))
}
