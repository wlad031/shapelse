package dev.vgerasimov.shapelse
package combine
package implicits

import dev.vgerasimov.shapelse.empty.Empty
import shapeless.{ <:!<, =:!= }

trait CombinersWithTuples {

  private type NotEmpty[A] = A =:!= Empty.type
  private type NotTuple2[A] = A <:!< (Any, Any)

  implicit def toTuple2[A : NotEmpty : NotTuple2, B : NotEmpty : NotTuple2]: Combiner[A, B, (A, B)] =
    (v1: A, v2: B) => (v1, v2)

  implicit def toTuple3[A, B, C]: Combiner[(A, B), C, (A, B, C)] = (v1: (A, B), v2: C) => (v1._1, v1._2, v2)
}
