package dev.vgerasimov.shapelse
package values
package implicits

import shapeless.labelled.FieldType
import shapeless.ops.coproduct.Length
import shapeless.ops.nat.ToInt
import shapeless.{ :+:, CNil, Coproduct, Inl, Inr, Lazy, Nat, Witness }

trait CoproductValueShapeEncoders {
  import CoproductValueShapeEncoder.instance

  implicit val cnilValueShapeEncoder: CoproductValueShapeEncoder[CNil] =
    instance((_: CNil) => CoproductShape(NilValue, List()))

  implicit def coproductValueShapeEncoder[K <: Symbol, N <: Nat, H, T <: Coproduct](
    implicit
    witness: Witness.Aux[K],
    length: Length.Aux[T, N],
    toInt: ToInt[N],
    hEncoder: Lazy[ValueShapeEncoder[H]],
    tEncoder: CoproductValueShapeEncoder[T]
  ): CoproductValueShapeEncoder[FieldType[K, H] :+: T] =
    instance((a: FieldType[K, H] :+: T) => {
      val childs = a match {
        case Inl(h) =>
          hEncoder.value.encode(h) :: (0 until toInt()).map(_ => ProductShape(NilValue: Value, List())).toList
        case Inr(t) => ProductShape(NilValue: Value, List()) :: tEncoder.encode(t).childs
      }
      CoproductShape(
        NilValue,
        childs
      )
    })
}
