package dev.vgerasimov.shapelse
package structure

package object implicits {

  /** Contains all needed implicits for automatic [[Schema]] derivation. */
  object all
      extends PrimitiveSchemaEncoders
      with GenericSchemaEncoders
      with HListSchemaEncoders
      with CoproductSchemaEncoders

  /** Contains implicits for [[Schema]] derivation for primitive types. */
  object primitives extends PrimitiveSchemaEncoders
}
