package dev.vgerasimov.shapelse
package empty
package implicits

import shapeless.{ <:!<, Coproduct, Generic, HList, Lazy }

trait GenericEmptyShapeEncoders {

  implicit def emptyListShapeEncoder[T](
    implicit
    tEncoder: Lazy[ShapeEncoder[Empty.type, T]]
  ): ShapeEncoder[Empty.type, List[T]] = ShapeEncoder.instance(ListShape(Empty, List(tEncoder.value.encode)))

  implicit def genericEmptyProductShapeEncoder[A, Repr <: HList](
    implicit
    gen: Generic.Aux[A, Repr],
    reprEncoder: Lazy[ProductShapeEncoder[Empty.type, Repr]]
  ): ProductShapeEncoder[Empty.type, A] = ProductShapeEncoder.instance(reprEncoder.value.encode)

  implicit def genericEmptyCoproductShapeEncoder[A, Repr <: Coproduct](
    implicit
    gen: Generic.Aux[A, Repr],
    reprEncoder: Lazy[CoproductShapeEncoder[Empty.type, Repr]],
    notList: A <:!< List[_]
  ): CoproductShapeEncoder[Empty.type, A] = CoproductShapeEncoder.instance(reprEncoder.value.encode)
}
