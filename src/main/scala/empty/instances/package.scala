package dev.vgerasimov.shapelse
package empty

import values.{ NilValue, Value }

package object instances {
  implicit val emptibleEmpty: Emptible[Empty.type] = Emptible(Empty)
  implicit val emptibleString: Emptible[String] = Emptible("")
  implicit val emptibleValue: Emptible[Value] = Emptible(NilValue)
  implicit def emptibleOption[A]: Emptible[Option[A]] = Emptible(None)

  implicit def emptibleTuple2[A, B](
    implicit
    e1: Emptible[A],
    e2: Emptible[B]
  ): Emptible[(A, B)] = Emptible((e1.empty, e2.empty))

  implicit def emptibleTuple3[A, B, C](
    implicit
    e1: Emptible[A],
    e2: Emptible[B],
    e3: Emptible[C]
  ): Emptible[(A, B, C)] = Emptible((e1.empty, e2.empty, e3.empty))
}
