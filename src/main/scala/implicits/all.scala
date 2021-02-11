package dev.vgerasimov.scmc
package implicits

/** Contains all needed implicits for automatic [[Field]] derivation. */
object all extends PrimitiveFieldEncoders with GenericFieldEncoders with HListFieldEncoders with CoproductFieldEncoders
