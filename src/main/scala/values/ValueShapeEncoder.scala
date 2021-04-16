package dev.vgerasimov.shapelse
package values

trait ValueShapeEncoder[A] extends ShapeInstanceEncoder[Value, A]

object ValueShapeEncoder {
  def instance[A](f: A => Shape[Value]): ValueShapeEncoder[A] = (a: A) => f(a)
}

trait PrimitiveValueShapeEncoder[A] extends ValueShapeEncoder[A] {
  override def encode(a: A): PrimitiveShape[Value]
}

object PrimitiveValueShapeEncoder {
  def instance[A](shape: A => PrimitiveShape[Value]): PrimitiveValueShapeEncoder[A] = (a: A) => shape(a)
}

trait ProductValueShapeEncoder[A] extends ValueShapeEncoder[A] {
  def encode(a: A): ProductShape[Value]
}

object ProductValueShapeEncoder {
  def instance[A](shape: A => ProductShape[Value]): ProductValueShapeEncoder[A] = (a: A) => shape(a)
}

trait CoproductValueShapeEncoder[A] extends ValueShapeEncoder[A] {
  def encode(a: A): CoproductShape[Value]
}

object CoproductValueShapeEncoder {
  def instance[A](shape: A => CoproductShape[Value]): CoproductValueShapeEncoder[A] = (a: A) => shape(a)
}
