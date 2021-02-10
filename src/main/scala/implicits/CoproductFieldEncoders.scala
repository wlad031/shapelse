package dev.vgerasimov.scmc
package implicits

import shapeless.labelled.FieldType
import shapeless.{ :+:, CNil, Coproduct, Lazy, Witness }

trait CoproductFieldEncoders {

  implicit val cnilFieldEncoder: FieldEncoder[CNil] = FieldEncoder.instance(NilField)

  implicit def coproductFieldEncoder[K <: Symbol, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    hEncoder: Lazy[FieldEncoder[H]],
    tEncoder: FieldEncoder[T]
  ): FieldEncoder[FieldType[K, H] :+: T] = FieldEncoder.instance[FieldType[K, H] :+: T] {
    val h = hEncoder.value.encode
    tEncoder.encode match {
      case NilField => h
      case t        => EitherField(h, t)
    }
  }
}
