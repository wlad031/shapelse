package dev.vgerasimov.shapelse
package typenames

package object implicits {

  object all
      extends PrimitiveTypeNameShapeEncoders
      with GenericTypeNameShapeEncoders
      with HListTypeNameShapeEncoders
      with CoproductTypeNameShapeEncoders

  object primitives extends PrimitiveTypeNameShapeEncoders
}
