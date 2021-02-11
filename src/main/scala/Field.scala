package dev.vgerasimov.scmc

import scala.collection.immutable.ListMap

sealed trait Field

sealed trait PrimitiveField extends Field
case object BooleanField extends PrimitiveField
case object CharField extends PrimitiveField
case object StringField extends PrimitiveField
case object ShortField extends PrimitiveField
case object IntField extends PrimitiveField
case object LongField extends PrimitiveField
case object FloatField extends PrimitiveField
case object DoubleField extends PrimitiveField

final case class OptionField(field: Field) extends Field
final case class ListField(field: Field) extends Field
final case class ProductField(childs: ListMap[Symbol, Field]) extends Field
final case class CoproductField(childs: ListMap[Symbol, Field]) extends Field
