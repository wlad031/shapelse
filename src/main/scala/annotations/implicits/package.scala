package dev.vgerasimov.shapelse
package annotations

import structure.implicits.{ CoproductFieldEncoders, HListFieldEncoders, PrimitiveFieldEncoders }

package object implicits {

  object all
//      extends PrimitiveFieldEncoders
      extends GenericAnnotatedFieldEncoders
//      with HListFieldEncoders
//      with CoproductFieldEncoders
}
