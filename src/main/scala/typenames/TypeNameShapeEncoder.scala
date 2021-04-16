package dev.vgerasimov.shapelse
package typenames

trait TypeNameShapeEncoder[A] extends ShapeEncoder[String, A]

object TypeNameShapeEncoder {
  def instance[A, T](shape: => Shape[String]): TypeNameShapeEncoder[A] = new TypeNameShapeEncoder[A] {
    override def encode: Shape[String] = shape
  }
}

trait PrimitiveTypeNameShapeEncoder[A] extends TypeNameShapeEncoder[A] {
  override def encode: PrimitiveShape[String]
}

object PrimitiveTypeNameShapeEncoder {
  def instance[A, T](shape: => PrimitiveShape[String]): PrimitiveTypeNameShapeEncoder[A] =
    new PrimitiveTypeNameShapeEncoder[A] {
      override def encode: PrimitiveShape[String] = shape
    }
}

trait ProductTypeNameShapeEncoder[A] extends TypeNameShapeEncoder[A] {
  override def encode: ProductShape[String]
}

object ProductTypeNameShapeEncoder {
  def instance[A](shape: => ProductShape[String]): ProductTypeNameShapeEncoder[A] =
    new ProductTypeNameShapeEncoder[A] {
      override def encode: ProductShape[String] = shape
    }
}

trait CoproductTypeNameShapeEncoder[A] extends TypeNameShapeEncoder[A] {
  override def encode: CoproductShape[String]
}

object CoproductTypeNameShapeEncoder {
  def instance[A](shape: => CoproductShape[String]): CoproductTypeNameShapeEncoder[A] =
    new CoproductTypeNameShapeEncoder[A] {
      override def encode: CoproductShape[String] = shape
    }
}
