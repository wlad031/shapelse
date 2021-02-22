package dev.vgerasimov.shapelse
package structure
package implicits

import dev.vgerasimov.shapelse.empty.Emptible
import shapeless.LabelledGeneric

/** Contains implicits for [[Field]] derivation for generic types. */
trait GenericFieldEncoders {

  implicit def optionEmptyFieldEncoder[M : Emptible, A](
    implicit
    encoder: FieldEncoder[M, A]
  ): FieldEncoder[M, Option[A]] =
    FieldEncoder.instance(OptionField(Emptible.summon.empty, encoder.encode))

  implicit def seqEmptyFieldEncoder[M : Emptible, A](
    implicit
    encoder: FieldEncoder[M, A]
  ): FieldEncoder[M, Seq[A]] =
    FieldEncoder.instance(ListField(Emptible.summon.empty, encoder.encode))

  implicit def genericEmptyFieldEncoder[M : Emptible, A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprEncoder: FieldEncoder[M, Repr]
  ): FieldEncoder[M, A] = FieldEncoder.instance[M, A] { reprEncoder.encode }
}
