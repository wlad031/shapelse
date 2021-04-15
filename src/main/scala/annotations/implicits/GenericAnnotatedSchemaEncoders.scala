package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.{ Annotation, Annotations, Coproduct, Generic, HList, Lazy }

trait GenericAnnotatedSchemaEncoders {

  implicit def genericAnnotatedProductEncoder[A, T, ARepr <: HList, TRepr <: HList](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: Lazy[ProductAnnotatedSchemaEncoder[A, TRepr]],
    annotation: Annotation[Option[A], T],
    annotations: Annotations.Aux[A, T, ARepr],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): ProductAnnotatedSchemaEncoder[A, T] = ProductAnnotatedSchemaEncoder.instance {
    val repr = reprEncoder.value.encode
    val childs =
      (repr.childs zip annotations().toList)
        .map({ case (schema, annotation) => schema.mapMetaOnly(annotation.orElse(_)) })
    ProductSchema(annotation(), childs)
  }

  implicit def genericAnnotatedCoproductEncoder[A, T, ARepr <: HList, TRepr <: Coproduct](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: Lazy[CoproductAnnotatedSchemaEncoder[A, TRepr]],
    annotation: Annotation[Option[A], T],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): CoproductAnnotatedSchemaEncoder[A, T] = CoproductAnnotatedSchemaEncoder.instance {
    val repr = reprEncoder.value.encode
    CoproductSchema(annotation(), repr.childs)
  }
}
