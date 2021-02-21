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

object BooleanField {
  def empty: BooleanField[Empty] = BooleanField(Empty())
}

case class CharField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): CharField[M] = CharField(meta)
}

object CharField {
  def empty: CharField[Empty] = CharField(Empty())
}

case class StringField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): StringField[M] = StringField(meta)
}

object StringField {
  def empty: StringField[Empty] = StringField(Empty())
}

case class ShortField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): ShortField[M] = ShortField(meta)
}

object ShortField {
  def empty: ShortField[Empty] = ShortField(Empty())
}

case class IntField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): IntField[M] = IntField(meta)
}

object IntField {
  def empty: IntField[Empty] = IntField(Empty())
}

case class LongField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): LongField[M] = LongField(meta)
}

object LongField {
  def empty: LongField[Empty] = LongField(Empty())
}

case class FloatField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): FloatField[M] = FloatField(meta)
}

object FloatField {
  def empty: FloatField[Empty] = FloatField(Empty())
}

case class DoubleField[M](override val meta: M) extends PrimitiveField[M] {
  override def copyWithMeta(meta: M): DoubleField[M] = DoubleField(meta)
}

object DoubleField {
  def empty: DoubleField[Empty] = DoubleField(Empty())
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

object ProductField {
  def empty(childs: ListMap[Symbol, Field[Empty]]): ProductField[Empty] = ProductField(Empty(), childs)
}

final case class CoproductField[M](override val meta: M, childs: Map[Symbol, Field[M]]) extends Field[M] {
  override def copyWithMeta(meta: M): CoproductField[M] = CoproductField(meta, childs)
}

object CoproductField {
  def empty(childs: Map[Symbol, Field[Empty]]): CoproductField[Empty] = CoproductField(Empty(), childs)
}
