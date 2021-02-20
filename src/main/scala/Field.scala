package dev.vgerasimov.shapelse

import scala.collection.immutable.ListMap

sealed trait Field[M] {
  def meta: M
  def map[M1](f: M => M1): Field[M1] = ???
  def copyWithMeta(meta: M): Field[M]
}

sealed trait PrimitiveField[M] extends Field[M]
case class BooleanField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): BooleanField[M] = BooleanField(meta)
}
case class CharField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): CharField[M] = CharField(meta)
}
case class StringField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): StringField[M] = StringField(meta)
}
case class ShortField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): ShortField[M] = ShortField(meta)
}
case class IntField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): IntField[M] = IntField(meta)
}
case class LongField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): LongField[M] = LongField(meta)
}
case class FloatField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): FloatField[M] = FloatField(meta)
}
case class DoubleField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): DoubleField[M] = DoubleField(meta)
}

final case class OptionField[M](override val meta: M, field: Field[M]) extends Field[M] {
  override def copyWithMeta(meta: M): OptionField[M] = OptionField(meta, field)
}
final case class ListField[M](override val meta: M, field: Field[M]) extends Field[M] {
  override def copyWithMeta(meta: M): ListField[M] = ListField(meta, field)
}
final case class ProductField[M](override val meta: M, childs: ListMap[Symbol, Field[M]]) extends Field[M] {
  override def copyWithMeta(meta: M): ProductField[M] = ProductField(meta, childs)
}
final case class CoproductField[M](override val meta: M, childs: Map[Symbol, Field[M]]) extends Field[M] {
  override def copyWithMeta(meta: M): CoproductField[M] = CoproductField(meta, childs)
}
