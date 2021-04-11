package dev.vgerasimov.shapelse
package combine
package implicits

import dev.vgerasimov.shapelse.empty.Empty

trait CombinersWithEmpty {

  /** Omits first [[Empty]] object while combining. */
  implicit def firstEmpty[A]: Combiner[Empty.type, A, A] = (_: Empty.type, v2: A) => v2

  /** Omits second [[Empty]] object while combining. */
  implicit def secondEmpty[A]: Combiner[A, Empty.type, A] = (v1: A, _: Empty.type) => v1
}
