package dev.vgerasimov

import dev.vgerasimov.shapelse.empty.Empty

import scala.annotation.StaticAnnotation

package object shapelse {

  def structureFieldEncoder[A](implicit ev: FieldEncoder[Empty, A]): FieldEncoder[Empty, A] = ev
  def annotationFieldEncoder[Ann <: StaticAnnotation, A](
    implicit ev: FieldEncoder[Option[Ann], A]
  ): FieldEncoder[Option[Ann], A] = ev

}
