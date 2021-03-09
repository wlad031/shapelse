package dev.vgerasimov

import dev.vgerasimov.shapelse.empty.Empty

import scala.annotation.StaticAnnotation

package object shapelse {

  def structureSchemaEncoder[A](implicit ev: SchemaEncoder[Empty, A]): SchemaEncoder[Empty, A] = ev
  def annotationSchemaEncoder[Ann <: StaticAnnotation, A](
    implicit ev: SchemaEncoder[Option[Ann], A]
  ): SchemaEncoder[Option[Ann], A] = ev

}
