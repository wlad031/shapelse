package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.{ LabelledGeneric, Lazy }

/** Contains implicits for [[Schema]] derivation for generic types. */
trait GenericNamesSchemaEncoders {
  import NameSchemaEncoder.instance

  implicit def optionNameSchemaEncoder[A](
    implicit
    encoder: Lazy[NameSchemaEncoder[A]]
  ): NameSchemaEncoder[Option[A]] = {
    val schema = encoder.value.encode
    instance(OptionSchema(schema.meta, Some(schema)))
  }

  implicit def listNameSchemaEncoder[A](
    implicit
    encoder: Lazy[NameSchemaEncoder[A]]
  ): NameSchemaEncoder[List[A]] = {
    val schema = encoder.value.encode
    instance(ListSchema(schema.meta, List(schema)))
  }

  implicit def genericNameSchemaEncoder[A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprEncoder: Lazy[NameSchemaEncoder[Repr]]
  ): NameSchemaEncoder[A] = instance(reprEncoder.value.encode)
}
