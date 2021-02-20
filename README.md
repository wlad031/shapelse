# Shapelse

[![ci](https://img.shields.io/github/workflow/status/wlad031/shapelse/Scala%20CI?label=CI&logo=GitHub&style=flat-square)](https://github.com/wlad031/shapelse/actions)
[![codecov](https://img.shields.io/codecov/c/github/wlad031/shapelse?label=cov&logo=Codecov&style=flat-square)](https://codecov.io/gh/wlad031/shapelse)

Shapelse (_shape_ + _else_) is a small abstraction layer on top of [Shapeless](https://github.com/milessabin/shapeless). As an abstraction layer it has less freedom in usage but in some cases it's more convenient to use.

Shapelse allows deriving strictly defined *Schema*, or *Field*, for your ADTs ([what are ADTs?](https://alvinalexander.com/scala/fp-book/algebraic-data-types-adts-in-scala/)).

## Example

Let's say, you have the following annotation and ADT:
```scala
case class says(s: String) extends StaticAnnotation

sealed trait Animal
@says("oof") case class Dog(name: String) extends Animal
@says("meow") case class Cat(name: String) extends Animal
```

First, import implicits:
```scala 
import dev.vgerasimov.shapelse.annotations.implicits.all._
import dev.vgerasimov.shapelse.structure.implicits.all._
```

Now you are able to build your encoder:
```scala
 val encoder = structureFieldEncoder[Animal]
    .combine(annotationFieldEncoder[says, Animal])
```

Get your `Field`:
```scala
val field = encoder.encode

// Output:
// CoproductField(
//   None,                      <- "Animal" doesn't have "@says"
//   ListMap(                   <- children of "Animal"
//     Symbol(Cat) ->           <- name of the first child
//       ProductField(
//         Some(says(meow)),    <- "Cat" has "@says"
//         ListMap(             <- fields of "Cat" class
//           Symbol(name) -> StringField(None))), 
//     Symbol(Dog) ->           <- name of the second child
//       ProductField(
//         Some(says(oof)),     <- "Dog" has "@says" 
//         ListMap(             <- fields of "Dog" class
//           Symbol(name) -> StringField(None)))))
```