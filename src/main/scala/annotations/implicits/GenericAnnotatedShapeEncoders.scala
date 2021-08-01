package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.{<:!<, Annotation, Annotations, Coproduct, Generic, HList, Lazy}

trait GenericAnnotatedShapeEncoders {

  implicit def listAnnotatedShapeEncoder[A, T](
    implicit
    encoder: Lazy[AnnotatedShapeEncoder[A, T]]
  ): AnnotatedShapeEncoder[A, List[T]] = {
    val shape = encoder.value.encode
    AnnotatedShapeEncoder.instance(ListShape(shape.meta, List(shape)))
  }

  implicit def genericAnnotatedProductEncoder[A, T, ARepr <: HList, TRepr <: HList](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: Lazy[ProductAnnotatedShapeEncoder[A, TRepr]],
    annotation: Annotation[Option[A], T],
    annotations: Annotations.Aux[A, T, ARepr],
    toList: ToTraversable.Aux[ARepr, List, Option[A]],
    notList: T <:!< List[_]
  ): ProductAnnotatedShapeEncoder[A, T] = ProductAnnotatedShapeEncoder.instance {
    val repr = reprEncoder.value.encode
    val childs =
      (repr.childs zip annotations().toList)
        .map({ case (shape, annotation) => shape.mapMetaOnly(annotation.orElse(_)) })
    ProductShape(annotation(), childs)
  }

  implicit def genericAnnotatedCoproductEncoder[A, T, ARepr <: HList, TRepr <: Coproduct](
    implicit
    gen: Generic.Aux[T, TRepr],
    reprEncoder: Lazy[CoproductAnnotatedShapeEncoder[A, TRepr]],
    annotation: Annotation[Option[A], T],
    toList: ToTraversable.Aux[ARepr, List, Option[A]],
    notList: T <:!< List[_]
  ): CoproductAnnotatedShapeEncoder[A, T] = CoproductAnnotatedShapeEncoder.instance {
    val repr = reprEncoder.value.encode
    CoproductShape(annotation(), repr.childs)
  }
}
