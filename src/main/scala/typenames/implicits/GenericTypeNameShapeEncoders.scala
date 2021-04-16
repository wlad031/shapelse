package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ <:!<, Coproduct, Generic, HList }

import scala.reflect.runtime.{ universe => un }

trait GenericTypeNameShapeEncoders {

  implicit def genericTypeNameListShapeEncoder[T](
    implicit
    tEncoder: TypeNameShapeEncoder[T],
    wtt: un.WeakTypeTag[List[T]]
  ): TypeNameShapeEncoder[List[T]] =
    TypeNameShapeEncoder.instance(ListShape(un.weakTypeOf[List[T]].toString, List(tEncoder.encode)))

  implicit def genericTypeNameProductEncoder[T : un.WeakTypeTag, TRepr <: HList](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: ProductTypeNameShapeEncoder[TRepr],
    notList: T <:!< List[_]
  ): ProductTypeNameShapeEncoder[T] =
    ProductTypeNameShapeEncoder.instance(ProductShape(un.weakTypeOf[T].toString, reprEncoder.encode.childs))

  implicit def genericTypeNameCoproductEncoder[T : un.WeakTypeTag, TRepr <: Coproduct](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: CoproductTypeNameShapeEncoder[TRepr],
    notList: T <:!< List[_]
  ): CoproductTypeNameShapeEncoder[T] =
    CoproductTypeNameShapeEncoder.instance(CoproductShape(un.weakTypeOf[T].toString, reprEncoder.encode.childs))
}
