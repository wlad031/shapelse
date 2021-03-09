package dev.vgerasimov.shapelse

import combine.{ CombinedSchemaEncoder, Combiner }

trait SchemaEncoder[M, A] {
  def encode: Schema[M]

  def combine[M1, MR](that: SchemaEncoder[M1, A])(
    implicit metaCombiner: Combiner[M, M1, MR]
  ): CombinedSchemaEncoder[MR, A] = CombinedSchemaEncoder.instance(left = this, right = that)
}

object SchemaEncoder {
  def instance[M, A](schema: => Schema[M]): SchemaEncoder[M, A] = new SchemaEncoder[M, A] {
    override lazy val encode: Schema[M] = schema
  }
}

trait PrimitiveSchemaEncoder[M, A] extends SchemaEncoder[M, A] {
  override def encode: PrimitiveSchema[M]
}

object PrimitiveSchemaEncoder {
  def instance[M, A](schema: PrimitiveSchema[M]): PrimitiveSchemaEncoder[M, A] = new PrimitiveSchemaEncoder[M, A] {
    override val encode: PrimitiveSchema[M] = schema
  }
}

trait ProductSchemaEncoder[M, A] extends SchemaEncoder[M, A] {
  override def encode: ProductSchema[M]
}

object ProductSchemaEncoder {
  def instance[M, A](schema: ProductSchema[M]): ProductSchemaEncoder[M, A] = new ProductSchemaEncoder[M, A] {
    override val encode: ProductSchema[M] = schema
  }
}

trait CoproductSchemaEncoder[M, A] extends SchemaEncoder[M, A] {
  override def encode: CoproductSchema[M]
}

object CoproductSchemaEncoder {
  def instance[M, A](schema: CoproductSchema[M]): CoproductSchemaEncoder[M, A] = new CoproductSchemaEncoder[M, A] {
    override val encode: CoproductSchema[M] = schema
  }
}
