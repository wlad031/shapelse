package dev.vgerasimov.shapelse
package values

package object implicits {

  object all
      extends PrimitiveValueShapeEncoders
      with GenericValueShapeEncoders
      with HListValueShapeEncoders
      with CoproductValueShapeEncoders

  object primitives extends PrimitiveValueShapeEncoders
}
