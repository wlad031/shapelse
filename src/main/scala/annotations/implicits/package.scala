package dev.vgerasimov.shapelse
package annotations

import structure.implicits.{ CoproductSchemaEncoders, HListSchemaEncoders, PrimitiveSchemaEncoders }

package object implicits {

  object all
//      extends PrimitiveSchemaEncoders
      extends GenericAnnotatedSchemaEncoders
//      with HListSchemaEncoders
//      with CoproductSchemaEncoders
}
