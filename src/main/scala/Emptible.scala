package dev.vgerasimov.shapelse

/** Represents that some type has "empty" state. */
trait Emptible[E] {
  def empty: E
}

/** Contains factories for [[Emptible]] objects. */
object Emptible {

  /** Constructs [[Emptible]] from the given "empty" state. */
  def apply[E](e: => E): Emptible[E] = new Emptible[E] {
    override def empty: E = e
  }

  /** Summoner for the [[Emptible]] instances. */
  def summon[E](implicit ev: Emptible[E]): Emptible[E] = ev

  // TODO: move to the better place
  /** [[Emptible]] instance for the [[Option]]. */
  implicit def emptibleOption[A]: Emptible[Option[A]] = new Emptible[Option[A]] {
    override def empty: Option[A] = None
  }
}
