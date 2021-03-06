package dev.vgerasimov.shapelse
package values

sealed trait Value

case object NilValue extends Value
final case class BooleanValue(value: Boolean) extends Value
final case class CharValue(value: Char) extends Value
final case class StringValue(value: String) extends Value
final case class ByteValue(value: Byte) extends Value
final case class ShortValue(value: Short) extends Value
final case class IntValue(value: Int) extends Value
final case class LongValue(value: Long) extends Value
final case class FloatValue(value: Float) extends Value
final case class DoubleValue(value: Double) extends Value
case object ListValue extends Value
case object ProductValue extends Value
case object CoproductValue extends Value
