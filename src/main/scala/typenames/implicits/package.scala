package dev.vgerasimov.shapelse
package typenames

package object implicits {

  object all
      extends PrimitiveTypeNameSchemaEncoders
      with GenericTypeNameSchemaEncoders
      with HListTypeNameSchemaEncoders
      with CoproductTypeNameSchemaEncoders
}
