package dev.vgerasimov.shapelse

import combine.Combiner

/** Represents purely "empty" state. The same as "Tuple0". */
final case class Empty()

/** Contains useful implicits for [[Empty]] objects. */
object Empty {

  /** [[Emptible]] instance for [[Empty]]. */
  implicit val emptible: Emptible[Empty] = Emptible(Empty())

  /** Omits first [[Empty]] object while combining. */
  implicit def firstEmpty[A]: Combiner[Empty, A, A] = (_: Empty, v2: A) => v2

  /** Omits second [[Empty]] object while combining. */
  implicit def secondEmpty[A]: Combiner[A, Empty, A] = (v1: A, _: Empty) => v1
}
