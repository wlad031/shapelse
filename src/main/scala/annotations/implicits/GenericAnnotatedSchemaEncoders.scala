package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.{ Annotation, Annotations, Coproduct, HList, LabelledGeneric }

trait GenericAnnotatedSchemaEncoders {

  implicit def genericAnnotatedProductEncoder[A, T, ARepr <: HList, TRepr <: HList](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: ProductSchemaEncoder[Option[A], TRepr],
    annotation: Annotation[Option[A], T],
    annotations: Annotations.Aux[A, T, ARepr],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): AnnotatedSchemaEncoder[A, T] = AnnotatedSchemaEncoder.instance {
    val repr = reprEncoder.encode
    val childs =
      (repr.childs zip annotations().toList)
        .map({ case (schema, annotation) => schema.map(_ => annotation) })
    ProductSchema(annotation(), childs)
  }

  implicit def genericAnnotatedCoproductEncoder[A, T, ARepr <: HList, TRepr <: Coproduct](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: CoproductSchemaEncoder[Option[A], TRepr],
    annotation: Annotation[Option[A], T],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): AnnotatedSchemaEncoder[A, T] = AnnotatedSchemaEncoder.instance {
    val repr = reprEncoder.encode
    CoproductSchema(annotation(), repr.childs)
  }
}
