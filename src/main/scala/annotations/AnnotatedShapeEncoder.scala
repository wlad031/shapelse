package dev.vgerasimov.shapelse
package annotations

trait AnnotatedShapeEncoder[A, T] extends ShapeEncoder[Option[A], T]

object AnnotatedShapeEncoder {
  def instance[A, T](shape: => Shape[Option[A]]): AnnotatedShapeEncoder[A, T] = new AnnotatedShapeEncoder[A, T] {
    override def encode: Shape[Option[A]] = shape
  }
}

trait PrimitiveAnnotatedShapeEncoder[A, T] extends AnnotatedShapeEncoder[A, T] {
  override def encode: PrimitiveShape[Option[A]]
}

object PrimitiveAnnotatedShapeEncoder {
  def instance[A, T](shape: => PrimitiveShape[Option[A]]): PrimitiveAnnotatedShapeEncoder[A, T] =
    new PrimitiveAnnotatedShapeEncoder[A, T] {
      override def encode: PrimitiveShape[Option[A]] = shape
    }
}

trait ProductAnnotatedShapeEncoder[A, T] extends AnnotatedShapeEncoder[A, T] {
  override def encode: ProductShape[Option[A]]
}

object ProductAnnotatedShapeEncoder {
  def instance[A, T](shape: => ProductShape[Option[A]]): ProductAnnotatedShapeEncoder[A, T] =
    new ProductAnnotatedShapeEncoder[A, T] {
      override def encode: ProductShape[Option[A]] = shape
    }
}

trait CoproductAnnotatedShapeEncoder[A, T] extends AnnotatedShapeEncoder[A, T] {
  override def encode: CoproductShape[Option[A]]
}

object CoproductAnnotatedShapeEncoder {
  def instance[A, T](shape: => CoproductShape[Option[A]]): CoproductAnnotatedShapeEncoder[A, T] =
    new CoproductAnnotatedShapeEncoder[A, T] {
      override def encode: CoproductShape[Option[A]] = shape
    }
}
