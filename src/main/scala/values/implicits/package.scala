package dev.vgerasimov.shapelse
package values

package object implicits {

  object all
      extends PrimitiveValueSchemaEncoders
      with GenericValueSchemaEncoders
      with HListValueSchemaEncoders
      with CoproductValueSchemaEncoders

  object primitives extends PrimitiveValueSchemaEncoders
}
