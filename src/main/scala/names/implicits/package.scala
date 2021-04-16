package dev.vgerasimov.shapelse
package names

package object implicits {

  /** Contains all needed implicits for automatic [[Shape]] derivation. */
  object all
      extends PrimitiveNamesShapeEncoders
      with GenericNamesShapeEncoders
      with HListNamesShapeEncoders
      with CoproductNamesShapeEncoders

  /** Contains implicits for [[Shape]] derivation for primitive types. */
  object primitives extends PrimitiveNamesShapeEncoders
}
