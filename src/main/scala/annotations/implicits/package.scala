package dev.vgerasimov.shapelse
package annotations

package object implicits {

  object all
      extends PrimitiveAnnotatedShapeEncoders
      with GenericAnnotatedShapeEncoders
      with HListAnnotatedShapeEncoders
      with CoproductAnnotatedShapeEncoders

  object primitives extends PrimitiveAnnotatedShapeEncoders
}
