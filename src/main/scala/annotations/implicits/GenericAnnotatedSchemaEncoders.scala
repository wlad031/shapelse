package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.{ Annotation, Annotations, Coproduct, HList, LabelledGeneric }

import scala.collection.immutable.ListMap

trait GenericAnnotatedSchemaEncoders {

  implicit def genericProductEncoder[A, T, ARepr <: HList, TRepr <: HList](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: ProductSchemaEncoder[Option[A], TRepr],
    annotation: Annotation[Option[A], T],
    annotations: Annotations.Aux[A, T, ARepr],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): AnnotatedSchemaEncoder[A, T] = AnnotatedSchemaEncoder.instance {
    val repr = reprEncoder.encode
    val childs = ListMap.from {
      (repr.childs zip annotations().toList)
        .map({ case ((symbol, schema), annotation) => (symbol, schema.map(_ => annotation)) })
        .toMap
    }
    ProductSchema(annotation(), childs)
  }

  implicit def genericCoproductEncoder[A, T, ARepr <: HList, TRepr <: Coproduct](
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
