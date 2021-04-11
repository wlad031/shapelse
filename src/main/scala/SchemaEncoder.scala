package dev.vgerasimov.shapelse

trait SchemaInstanceEncoder[M, A] { self =>
  def encode(a: A): Schema[M]

  def map[M1](mapper: M => M1): SchemaInstanceEncoder[M1, A] = new SchemaInstanceEncoder[M1, A] {
    override def encode(a: A): Schema[M1] = self.encode(a).map(mapper)
  }
}

trait SchemaEncoder[M, A] extends SchemaInstanceEncoder[M, A] { self =>
  def encode: Schema[M]
  override def encode(a: A): Schema[M] = this.encode

  override def map[M1](mapper: M => M1): SchemaEncoder[M1, A] = new SchemaEncoder[M1, A] {
    override def encode: Schema[M1] = self.encode.map(mapper)
  }
}

object SchemaEncoder {
  def instance[M, A](schema: => Schema[M]): SchemaEncoder[M, A] = new SchemaEncoder[M, A] {
    override lazy val encode: Schema[M] = schema
  }
}

private[shapelse] trait PrimitiveSchemaEncoder[M, A] extends SchemaEncoder[M, A] {
  override def encode: PrimitiveSchema[M]
}

private[shapelse] object PrimitiveSchemaEncoder {
  def instance[M, A](schema: PrimitiveSchema[M]): PrimitiveSchemaEncoder[M, A] = new PrimitiveSchemaEncoder[M, A] {
    override val encode: PrimitiveSchema[M] = schema
  }
}

private[shapelse] trait ProductSchemaEncoder[M, A] extends SchemaEncoder[M, A] {
  override def encode: ProductSchema[M]
}

private[shapelse] object ProductSchemaEncoder {
  def instance[M, A](schema: ProductSchema[M]): ProductSchemaEncoder[M, A] = new ProductSchemaEncoder[M, A] {
    override val encode: ProductSchema[M] = schema
  }
}

private[shapelse] trait CoproductSchemaEncoder[M, A] extends SchemaEncoder[M, A] {
  override def encode: CoproductSchema[M]
}

private[shapelse] object CoproductSchemaEncoder {
  def instance[M, A](schema: CoproductSchema[M]): CoproductSchemaEncoder[M, A] = new CoproductSchemaEncoder[M, A] {
    override val encode: CoproductSchema[M] = schema
  }
}
