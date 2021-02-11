package dev.vgerasimov.scmc

import scala.collection.immutable.ListMap

trait FieldsDerivationSuite {

  def assertFields(actual: ListMap[Symbol, Field], expected: ListMap[String, Field]): Unit = {
    assert(actual.map({ case (k, v) => (k.name, v) }) == expected)
  }
}
