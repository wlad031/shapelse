# Shapelse

[![build](https://img.shields.io/github/workflow/status/wlad031/shapelse/Scala%20CI?label=build&logo=GitHub&style=flat-square)](https://github.com/wlad031/shapelse/actions)
[![codecov](https://img.shields.io/codecov/c/github/wlad031/shapelse?label=codecov&logo=Codecov&style=flat-square)](https://codecov.io/gh/wlad031/shapelse)

> If you were looking for *Shapeless*, here is the [link](https://github.com/milessabin/shapeless).

Shapelse (_shape_ + _else_) is a small abstraction layer on top of [Shapeless](https://github.com/milessabin/shapeless).
As an abstraction layer it has less freedom in usage but in some cases it's more convenient to use.

Shapelse allows deriving strictly defined *Schema* for your
ADTs ([what are ADTs?](https://alvinalexander.com/scala/fp-book/algebraic-data-types-adts-in-scala/)).

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
val encoder = structureSchemaEncoder[Animal]
  .combine(annotationSchemaEncoder[says, Animal])
```

Get your `Schema`:

```scala
val schema = encoder.encode

// Output:
// CoproductSchema(
//   None,                      <- "Animal" doesn't have "@says"
//   Map(                       <- children of "Animal"
//     Symbol(Cat) ->           <- name of the first child
//       ProductSchema(
//         Some(says(meow)),    <- "Cat" has "@says"
//         ListMap(             <- schemas of "Cat" class
//           Symbol(name) -> StringSchema(None))),
//     Symbol(Dog) ->           <- name of the second child
//       ProductSchema(
//         Some(says(oof)),     <- "Dog" has "@says"
//         ListMap(             <- schemas of "Dog" class
//           Symbol(name) -> StringSchema(None)))))
```