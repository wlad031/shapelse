package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ <:!<, Coproduct, Generic, HList, Lazy }

trait GenericEmptySchemaEncoders {

  implicit def genericEmptyListSchemaEncoder[T](
    implicit
    tEncoder: Lazy[SchemaEncoder[Empty.type, T]]
  ): SchemaEncoder[Empty.type, List[T]] = SchemaEncoder.instance(ListSchema(Empty, List(tEncoder.value.encode)))

  implicit def genericEmptyProductSchemaEncoder[A, Repr <: HList](
    implicit
    gen: Generic.Aux[A, Repr],
    reprEncoder: Lazy[ProductSchemaEncoder[Empty.type, Repr]],
    notList: A <:!< List[_]
  ): ProductSchemaEncoder[Empty.type, A] = ProductSchemaEncoder.instance(reprEncoder.value.encode)

  implicit def genericEmptyCoproductSchemaEncoder[A, Repr <: Coproduct](
    implicit
    gen: Generic.Aux[A, Repr],
    reprEncoder: Lazy[CoproductSchemaEncoder[Empty.type, Repr]],
    notList: A <:!< List[_]
  ): CoproductSchemaEncoder[Empty.type, A] = CoproductSchemaEncoder.instance(reprEncoder.value.encode)
}
