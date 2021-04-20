package dev.vgerasimov.shapelse

/** Represents holder for some "meta" object. */
trait MetaProvider[A] {
  def meta: A
}

/** Contains factories for [[MetaProvider]] objects. */
object MetaProvider {

  /** Constructs [[MetaProvider]] from the given meta. */
  def apply[A](a: => A): MetaProvider[A] = new MetaProvider[A] {
    override def meta: A = a
  }
}
