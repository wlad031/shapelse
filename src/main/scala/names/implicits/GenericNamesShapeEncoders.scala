package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.{ LabelledGeneric, Lazy }

/** Contains implicits for [[Shape]] derivation for generic types. */
trait GenericNamesShapeEncoders {
  import NameShapeEncoder.instance

  implicit def listNameShapeEncoder[A](
    implicit
    encoder: Lazy[NameShapeEncoder[A]]
  ): NameShapeEncoder[List[A]] = {
    val shape = encoder.value.encode
    instance(ListShape(shape.meta, List(shape)))
  }

  implicit def genericNameShapeEncoder[A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprEncoder: Lazy[NameShapeEncoder[Repr]]
  ): NameShapeEncoder[A] = instance(reprEncoder.value.encode)
}
