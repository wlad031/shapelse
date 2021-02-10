package dev.vgerasimov.scmc
package implicits

import shapeless.LabelledGeneric

trait ComplexFieldEncoders {

  implicit def optionFieldEncoder[A](implicit ev: FieldEncoder[A]): FieldEncoder[Option[A]] =
    FieldEncoder.instance(OptionField(ev.encode))

  implicit def seqFieldEncoder[A](implicit ev: FieldEncoder[A]): FieldEncoder[Seq[A]] =
    FieldEncoder.instance(ListField(ev.encode))

  implicit def genericFieldEncoder[A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprFieldEncoder: FieldEncoder[Repr]
  ): FieldEncoder[A] = FieldEncoder.instance[A] { reprFieldEncoder.encode }
}
