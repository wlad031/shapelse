package dev.vgerasimov.scmc

package object implicits {

  /** Contains all needed implicits for automatic [[Field]] derivation. */
  object all
      extends PrimitiveFieldEncoders
      with GenericFieldEncoders
      with HListFieldEncoders
      with CoproductFieldEncoders

  /** Contains implicits for [[Field]] derivation for primitive types. */
  object primitives extends PrimitiveFieldEncoders
}
