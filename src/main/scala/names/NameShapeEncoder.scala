package dev.vgerasimov.shapelse
package names

trait NameShapeEncoder[A] extends ShapeEncoder[String, A]

object NameShapeEncoder {
  def instance[A, T](shape: => Shape[String]): NameShapeEncoder[A] = new NameShapeEncoder[A] {
    override def encode: Shape[String] = shape
  }
}

trait PrimitiveNameShapeEncoder[A] extends NameShapeEncoder[A] {
  override def encode: PrimitiveShape[String]
}

object PrimitiveNameShapeEncoder {
  def instance[A, T](shape: => PrimitiveShape[String]): PrimitiveNameShapeEncoder[A] =
    new PrimitiveNameShapeEncoder[A] {
      override def encode: PrimitiveShape[String] = shape
    }
}

trait ProductNameShapeEncoder[A] extends NameShapeEncoder[A] {
  override def encode: ProductShape[String]
}

object ProductNameShapeEncoder {
  def instance[A](shape: => ProductShape[String]): ProductNameShapeEncoder[A] =
    new ProductNameShapeEncoder[A] {
      override def encode: ProductShape[String] = shape
    }
}

trait CoproductNameShapeEncoder[A] extends NameShapeEncoder[A] {
  override def encode: CoproductShape[String]
}

object CoproductNameShapeEncoder {
  def instance[A](shape: => CoproductShape[String]): CoproductNameShapeEncoder[A] =
    new CoproductNameShapeEncoder[A] {
      override def encode: CoproductShape[String] = shape
    }
}
