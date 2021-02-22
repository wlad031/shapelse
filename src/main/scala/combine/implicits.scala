package dev.vgerasimov.shapelse
package combine

import shapeless.=:!=

object implicits {

  object all extends CombinersWithEmpty with CombinersWithTuples

  object empty extends CombinersWithEmpty
  object tuples extends CombinersWithTuples

  trait CombinersWithEmpty {

    /** Omits first [[Empty]] object while combining. */
    implicit def firstEmpty[A]: Combiner[Empty, A, A] = (_: Empty, v2: A) => v2

    /** Omits second [[Empty]] object while combining. */
    implicit def secondEmpty[A]: Combiner[A, Empty, A] = (v1: A, _: Empty) => v1
  }

  private type NotEmpty[A] = A =:!= Empty

  trait CombinersWithTuples {

    implicit def toTuple2[A : NotEmpty, B : NotEmpty]: Combiner[A, B, (A, B)] = (v1: A, v2: B) => (v1, v2)
  }
}
