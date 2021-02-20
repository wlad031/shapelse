package dev.vgerasimov.shapelse
package types

import scala.collection.immutable.ListMap

trait FieldsDerivationSuite {

  def assertFields[M](actual: ListMap[Symbol, Field[M]], expected: ListMap[String, Field[M]]): Unit = {
    assert(actual.map({ case (k, v) => (k.name, v) }) == expected)
  }
}
