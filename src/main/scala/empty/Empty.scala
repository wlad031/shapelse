package dev.vgerasimov.shapelse
package empty

/** Represents purely "empty" state. The same as "Tuple0". */
final case class Empty()

/** Contains useful implicits for [[Empty]] objects. */
object Empty {

  /** [[Emptible]] instance for [[Empty]]. */
  implicit val emptible: Emptible[Empty] = Emptible(Empty())
}
