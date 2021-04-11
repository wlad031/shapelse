package dev.vgerasimov.shapelse
package annotations

import names.implicits.PrimitiveNamesSchemaEncoders

package object implicits {

  object all
//      extends PrimitiveSchemaEncoders
      extends GenericAnnotatedSchemaEncoders
//      with HListSchemaEncoders
//      with CoproductSchemaEncoders
}
