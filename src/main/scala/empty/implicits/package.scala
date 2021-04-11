package dev.vgerasimov.shapelse
package empty

package object implicits {

  object all
      extends PrimitiveEmptySchemaEncoders
      with GenericEmptySchemaEncoders
      with HListEmptySchemaEncoders
      with CoproductEmptySchemaEncoders
}
