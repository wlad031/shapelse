package dev.vgerasimov.shapelse
package values
package implicits

import shapeless.labelled.FieldType
import shapeless.ops.coproduct.Length
import shapeless.ops.nat.ToInt
import shapeless.{ :+:, CNil, Coproduct, Inl, Inr, Lazy, Nat, Witness }

trait CoproductValueSchemaEncoders {
  import CoproductValueSchemaEncoder.instance

  implicit val cnilValueSchemaEncoder: CoproductValueSchemaEncoder[CNil] =
    instance((_: CNil) => CoproductSchema(NilValue, List()))

  implicit def coproductValueSchemaEncoder[K <: Symbol, N <: Nat, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    length: Length.Aux[T, N],
    toInt: ToInt[N],
    hEncoder: Lazy[ValueSchemaEncoder[H]],
    tEncoder: CoproductValueSchemaEncoder[T]
  ): CoproductValueSchemaEncoder[FieldType[K, H] :+: T] =
    instance((a: FieldType[K, H] :+: T) => {
      CoproductSchema(
        CoproductValue,
        a match {
          case Inl(h) =>
            hEncoder.value.encode(h) :: (0 until toInt()).map(_ => ProductSchema(NilValue: Value, List())).toList
          case Inr(t) => ProductSchema(NilValue: Value, List()) :: tEncoder.encode(t).childs
        }
      )
    })
}
