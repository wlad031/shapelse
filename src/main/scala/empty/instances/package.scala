package dev.vgerasimov.shapelse
package empty

import dev.vgerasimov.shapelse.values.{ NilValue, Value }

package object instances {
  implicit val emptibleEmpty: MetaProvider[Empty.type] = MetaProvider(Empty)
  implicit val emptibleString: MetaProvider[String] = MetaProvider("")
  implicit val emptibleValue: MetaProvider[Value] = MetaProvider(NilValue)
  implicit def emptibleOption[A]: MetaProvider[Option[A]] = MetaProvider(None)

  implicit def emptibleTuple2[A, B](
    implicit
    e1: MetaProvider[A],
    e2: MetaProvider[B]
  ): MetaProvider[(A, B)] = MetaProvider((e1.meta, e2.meta))

  implicit def emptibleTuple3[A, B, C](
    implicit
    e1: MetaProvider[A],
    e2: MetaProvider[B],
    e3: MetaProvider[C]
  ): MetaProvider[(A, B, C)] = MetaProvider((e1.meta, e2.meta, e3.meta))
}
