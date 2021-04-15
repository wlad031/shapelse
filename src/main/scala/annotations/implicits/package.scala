package dev.vgerasimov.shapelse
package annotations

package object implicits {

  object all
      extends PrimitiveAnnotatedSchemaEncoders
      with GenericAnnotatedSchemaEncoders
      with HListAnnotatedSchemaEncoders
      with CoproductAnnotatedSchemaEncoders

  object primitives extends PrimitiveAnnotatedSchemaEncoders
}
