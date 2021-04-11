package dev.vgerasimov.shapelse
package typenames
package implicits

import shapeless.{ Coproduct, Generic, HList }

import scala.reflect.runtime.{ universe => un }

trait GenericTypeNameSchemaEncoders {

  implicit def genericTypeNameProductEncoder[T : un.WeakTypeTag, TRepr <: HList](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: ProductTypeNameSchemaEncoder[TRepr]
  ): ProductTypeNameSchemaEncoder[T] =
    ProductTypeNameSchemaEncoder.instance(ProductSchema(un.weakTypeOf[T].toString, reprEncoder.encode.childs))

  implicit def genericTypeNameCoproductEncoder[T : un.WeakTypeTag, TRepr <: Coproduct](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: CoproductTypeNameSchemaEncoder[TRepr]
  ): CoproductTypeNameSchemaEncoder[T] =
    CoproductTypeNameSchemaEncoder.instance(CoproductSchema(un.weakTypeOf[T].toString, reprEncoder.encode.childs))
}
