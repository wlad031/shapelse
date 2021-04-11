package dev.vgerasimov.shapelse
package combine

package object implicits {

  object all extends CombinersWithEmpty with CombinersWithTuples

  object empty extends CombinersWithEmpty
  object tuples extends CombinersWithTuples
}
