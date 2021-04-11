package dev.vgerasimov.shapelse
package names
package implicits

import shapeless.{LabelledGeneric, Lazy}

/** Contains implicits for [[Schema]] derivation for generic types. */
trait GenericNamesSchemaEncoders {
  import NameSchemaEncoder.instance

  implicit def optionNameSchemaEncoder[A](
    implicit
    encoder: NameSchemaEncoder[A]
  ): NameSchemaEncoder[Option[A]] = {
    val schema = encoder.encode
    instance(OptionSchema(schema.meta, schema))
  }

  implicit def seqNameSchemaEncoder[A](
    implicit
    encoder: Lazy[NameSchemaEncoder[A]]
  ): NameSchemaEncoder[List[A]] = {
    val schema = encoder.value.encode
    instance(ListSchema(schema.meta, schema))
  }

  implicit def genericNameSchemaEncoder[A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprEncoder: Lazy[NameSchemaEncoder[Repr]]
  ): NameSchemaEncoder[A] = instance(reprEncoder.value.encode)
}
