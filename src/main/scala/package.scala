package dev.vgerasimov

import dev.vgerasimov.shapelse.annotations.AnnotatedSchemaEncoder
import dev.vgerasimov.shapelse.combine.implicits.EncodersCombiningOps
import dev.vgerasimov.shapelse.combine.implicits.empty._
import dev.vgerasimov.shapelse.empty.Empty
import dev.vgerasimov.shapelse.empty.instances._
import dev.vgerasimov.shapelse.names.NameSchemaEncoder
import dev.vgerasimov.shapelse.typenames.TypeNameSchemaEncoder
import dev.vgerasimov.shapelse.values.{ Value, ValueSchemaEncoder }

import scala.annotation.StaticAnnotation

package object shapelse extends EncodersCombiningOps {

  def namesSchemaEncoder[A](implicit ev: NameSchemaEncoder[A]): SchemaEncoder[String, A] = ev
  def typeNamesSchemaEncoder[A](implicit ev: TypeNameSchemaEncoder[A]): SchemaEncoder[String, A] = ev

  def annotationSchemaEncoder[Ann <: StaticAnnotation, A](
    implicit ev: AnnotatedSchemaEncoder[Ann, A]
  ): SchemaEncoder[Option[Ann], A] = ev

  def valueSchemaEncoder[A](
    implicit
    ev: ValueSchemaEncoder[A],
    emptyEncoder: SchemaEncoder[Empty.type, A]
  ): SchemaInstanceEncoder[Value, A] = emptyEncoder.combine(ev)

}
