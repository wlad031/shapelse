package dev.vgerasimov.shapelse

/** Represents "combine" function which is basically just a function of two arguments. */
trait Combiner[-A, -B, +R] extends ((A, B) => R) {
  def combine(v1: A, v2: B): R

  override def apply(v1: A, v2: B): R = combine(v1, v2)
}
