package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ <:!<, Coproduct, Generic, HList }

import scala.reflect.runtime.{ universe => un }

trait GenericTypeNameSchemaEncoders {

  implicit def genericTypeNameListSchemaEncoder[T](
    implicit
    tEncoder: TypeNameSchemaEncoder[T],
    wtt: un.WeakTypeTag[List[T]]
  ): TypeNameSchemaEncoder[List[T]] =
    TypeNameSchemaEncoder.instance(ListSchema(un.weakTypeOf[List[T]].toString, List(tEncoder.encode)))

  implicit def genericTypeNameProductEncoder[T : un.WeakTypeTag, TRepr <: HList](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: ProductTypeNameSchemaEncoder[TRepr],
    notList: T <:!< List[_]
  ): ProductTypeNameSchemaEncoder[T] =
    ProductTypeNameSchemaEncoder.instance(ProductSchema(un.weakTypeOf[T].toString, reprEncoder.encode.childs))

  implicit def genericTypeNameCoproductEncoder[T : un.WeakTypeTag, TRepr <: Coproduct](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: CoproductTypeNameSchemaEncoder[TRepr],
    notList: T <:!< List[_]
  ): CoproductTypeNameSchemaEncoder[T] =
    CoproductTypeNameSchemaEncoder.instance(CoproductSchema(un.weakTypeOf[T].toString, reprEncoder.encode.childs))
}
