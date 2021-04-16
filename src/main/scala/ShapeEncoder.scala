package dev.vgerasimov.shapelse

trait ShapeInstanceEncoder[M, A] { self =>
  def encode(a: A): Shape[M]

  def map[M1](mapper: M => M1): ShapeInstanceEncoder[M1, A] = (a: A) => self.encode(a).map(mapper)
}

trait ShapeEncoder[M, A] extends ShapeInstanceEncoder[M, A] { self =>
  def encode: Shape[M]
  override def encode(a: A): Shape[M] = this.encode

  override def map[M1](mapper: M => M1): ShapeEncoder[M1, A] = new ShapeEncoder[M1, A] {
    override def encode: Shape[M1] = self.encode.map(mapper)
  }
}

object ShapeEncoder {
  def instance[M, A](shape: => Shape[M]): ShapeEncoder[M, A] = new ShapeEncoder[M, A] {
    override lazy val encode: Shape[M] = shape
  }
}

private[shapelse] trait PrimitiveShapeEncoder[M, A] extends ShapeEncoder[M, A] {
  override def encode: PrimitiveShape[M]
}

private[shapelse] object PrimitiveShapeEncoder {
  def instance[M, A](shape: PrimitiveShape[M]): PrimitiveShapeEncoder[M, A] = new PrimitiveShapeEncoder[M, A] {
    override val encode: PrimitiveShape[M] = shape
  }
}

private[shapelse] trait ProductShapeEncoder[M, A] extends ShapeEncoder[M, A] {
  override def encode: ProductShape[M]
}

private[shapelse] object ProductShapeEncoder {
  def instance[M, A](shape: ProductShape[M]): ProductShapeEncoder[M, A] = new ProductShapeEncoder[M, A] {
    override val encode: ProductShape[M] = shape
  }
}

private[shapelse] trait CoproductShapeEncoder[M, A] extends ShapeEncoder[M, A] {
  override def encode: CoproductShape[M]
}

private[shapelse] object CoproductShapeEncoder {
  def instance[M, A](shape: CoproductShape[M]): CoproductShapeEncoder[M, A] = new CoproductShapeEncoder[M, A] {
    override val encode: CoproductShape[M] = shape
  }
}
