package dev.vgerasimov.shapelse
package empty

package object implicits {

  object all
      extends PrimitiveEmptyShapeEncoders
      with GenericEmptyShapeEncoders
      with HListEmptyShapeEncoders
      with CoproductEmptyShapeEncoders

  object primitives extends PrimitiveEmptyShapeEncoders
}
