package dev.vgerasimov.shapelse
package empty

object implicits {

  /** [[Emptible]] instance for the Option. */
  implicit def emptibleOption[A]: Emptible[Option[A]] = new Emptible[Option[A]] {
    override def empty: Option[A] = None
  }
}
