package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ Coproduct, Generic, HList, Lazy }

trait GenericEmptySchemaEncoders {

  implicit def genericEmptyProductSchemaEncoder[A, Repr <: HList](
    implicit
    gen: Generic.Aux[A, Repr],
    reprEncoder: Lazy[ProductSchemaEncoder[Empty.type, Repr]]
  ): ProductSchemaEncoder[Empty.type, A] = ProductSchemaEncoder.instance(reprEncoder.value.encode)

  implicit def genericEmptyCoproductSchemaEncoder[A, Repr <: Coproduct](
    implicit
    gen: Generic.Aux[A, Repr],
    reprEncoder: Lazy[CoproductSchemaEncoder[Empty.type, Repr]]
  ): CoproductSchemaEncoder[Empty.type, A] = CoproductSchemaEncoder.instance(reprEncoder.value.encode)
}
