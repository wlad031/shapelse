package dev.vgerasimov.shapelse
package annotations
package implicits

import shapeless.ops.hlist.ToTraversable
import shapeless.{ Annotation, Annotations, Coproduct, HList, LabelledGeneric }

import scala.collection.immutable.ListMap

trait GenericAnnotatedFieldEncoders {

  implicit def genericProductEncoder[A, T, ARepr <: HList, TRepr <: HList](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: ProductFieldEncoder[Option[A], TRepr],
    annotation: Annotation[Option[A], T],
    annotations: Annotations.Aux[A, T, ARepr],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): AnnotatedFieldEncoder[A, T] = AnnotatedFieldEncoder.instance {
    val repr = reprEncoder.encode
    val childs = ListMap.from {
      (repr.childs zip annotations().toList)
        .map({ case ((symbol, field), annotation) => (symbol, field.copyWithMeta(annotation)) })
        .toMap
    }
    ProductField(annotation(), childs)
  }

  implicit def genericCoproductEncoder[A, T, ARepr <: HList, TRepr <: Coproduct](
    implicit
    gen: LabelledGeneric.Aux[T, TRepr],
    reprEncoder: CoproductFieldEncoder[Option[A], TRepr],
    annotation: Annotation[Option[A], T],
    toList: ToTraversable.Aux[ARepr, List, Option[A]]
  ): AnnotatedFieldEncoder[A, T] = AnnotatedFieldEncoder.instance {
    val repr = reprEncoder.encode
    CoproductField(annotation(), repr.childs)
  }
}
