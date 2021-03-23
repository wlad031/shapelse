package dev.vgerasimov.shapelse
package structure
package implicits

import empty.Emptible

import shapeless.LabelledGeneric

/** Contains implicits for [[Schema]] derivation for generic types. */
trait GenericSchemaEncoders {

  implicit def optionEmptySchemaEncoder[M : Emptible, A](
    implicit
    encoder: SchemaEncoder[M, A]
  ): SchemaEncoder[M, Option[A]] =
    SchemaEncoder.instance(OptionSchema(Emptible.summon.empty, encoder.encode))

  implicit def seqEmptySchemaEncoder[M : Emptible, A](
    implicit
    encoder: SchemaEncoder[M, A]
  ): SchemaEncoder[M, Seq[A]] =
    SchemaEncoder.instance(ListSchema(Emptible.summon.empty, encoder.encode))

  implicit def genericEmptySchemaEncoder[M : Emptible, A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprEncoder: SchemaEncoder[M, Repr]
  ): SchemaEncoder[M, A] = SchemaEncoder.instance[M, A] { reprEncoder.encode }
}
