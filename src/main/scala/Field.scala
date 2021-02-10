package dev.vgerasimov.scmc

import scala.collection.immutable.ListMap

sealed trait Field

case object NilField extends Field
case object BooleanField extends Field
case object CharField extends Field
case object StringField extends Field
case object ShortField extends Field
case object IntField extends Field
case object LongField extends Field
case object FloatField extends Field
case object DoubleField extends Field
case class OptionField(field: Field) extends Field
case class ListField(field: Field) extends Field
case class ObjectField(fields: ListMap[Symbol, Field]) extends Field
case class EitherField(left: Field, right: Field) extends Field
