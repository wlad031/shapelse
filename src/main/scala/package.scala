package dev.vgerasimov

import dev.vgerasimov.shapelse.annotations.AnnotatedShapeEncoder
import dev.vgerasimov.shapelse.combine.implicits.empty._
import dev.vgerasimov.shapelse.combine.syntax.EncodersCombiningOps
import dev.vgerasimov.shapelse.empty.Empty
import dev.vgerasimov.shapelse.empty.instances._
import dev.vgerasimov.shapelse.names.NameShapeEncoder
import dev.vgerasimov.shapelse.typenames.TypeNameShapeEncoder
import dev.vgerasimov.shapelse.values.{ Value, ValueShapeEncoder }

import scala.annotation.StaticAnnotation

package object shapelse extends EncodersCombiningOps {

  def emptyShapeEncoder[A](implicit ev: ShapeEncoder[Empty.type, A]): ShapeEncoder[Empty.type, A] = ev

  def namesShapeEncoder[A](implicit ev: NameShapeEncoder[A]): ShapeEncoder[String, A] = ev

  def typeNamesShapeEncoder[A](implicit ev: TypeNameShapeEncoder[A]): ShapeEncoder[String, A] = ev

  def annotationShapeEncoder[Ann <: StaticAnnotation, A](
    implicit ev: AnnotatedShapeEncoder[Ann, A]
  ): ShapeEncoder[Option[Ann], A] = ev

  def valueShapeEncoder[A](
    implicit
    ev: ValueShapeEncoder[A],
    emptyEncoder: ShapeEncoder[Empty.type, A]
  ): ShapeInstanceEncoder[Value, A] = emptyEncoder.combine(ev)

  sealed trait CombiningError
  object CombiningError {
    final case class IncompatibleShapes[S1 <: Shape[_], S2 <: Shape[_]](left: S1, right: S2) extends CombiningError
    final case class IfEmptyNotProvided[S <: Shape[_]](shape: S) extends CombiningError
    final case class Multiple(errors: List[CombiningError]) extends CombiningError
  }
}
