package dev.vgerasimov.shapelse
package names

package object implicits {

  /** Contains all needed implicits for automatic [[Schema]] derivation. */
  object all
      extends PrimitiveNamesSchemaEncoders
      with GenericNamesSchemaEncoders
      with HListNamesSchemaEncoders
      with CoproductNamesSchemaEncoders

  /** Contains implicits for [[Schema]] derivation for primitive types. */
  object primitives extends PrimitiveNamesSchemaEncoders
}
