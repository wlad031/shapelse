# Shapelse

[![build](https://img.shields.io/github/workflow/status/wlad031/shapelse/Scala%20CI?label=build&logo=GitHub&style=flat-square)](https://github.com/wlad031/shapelse/actions)
[![codecov](https://img.shields.io/codecov/c/github/wlad031/shapelse?label=codecov&logo=Codecov&style=flat-square)](https://codecov.io/gh/wlad031/shapelse)
![latest](https://img.shields.io/github/v/tag/wlad031/shapelse?label=latest&style=flat-square)

> If you were looking for *Shapeless*, here is the [link](https://github.com/milessabin/shapeless).

Shapelse (_shape_ + _else_) is a small abstraction layer on top of [Shapeless](https://github.com/milessabin/shapeless).
As an abstraction layer it has less freedom in usage but in some cases it's more convenient to use.

Shapelse allows deriving strictly defined *Shape*s for your
ADTs ([what is ADT?](https://alvinalexander.com/scala/fp-book/algebraic-data-types-adts-in-scala/)).

> *Why?* Originally, I needed to implement automatic derivation for [Json Schema](https://json-schema.org/) encoders. But then I realized that similar structure can be used in different cases, for example, if you need print your data in table view with pretty column titles. That's why it was extracted into this library.

## Supported types

Currently, Shapelse supports the following types:

| Type | Comment |
|------|---------|
| primitives | `Boolean`, `Char`, `String`, `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`  | 
| case classes (products)<sup>*</sup> | |
| sealed traits extended by case classes (coproducts)<sup>*</sup> | |
| `Option`<sup>*</sup> | |
| `List`<sup>*</sup> | |

<sup>* Can contain any other supported types.</sup>

## Implemented shape encoders

### Empty

TBD

### Names

TBD

### Typenames

TBD

### Annotations

TBD

### Values

TBD

## What is *Shape*?

*Shape* is a simple algebraic data type:

![shape](/docs/images/shape-adt.png?raw=true)


## Example

Let's say, you have the following annotation and ADT:

```scala
case class says(s: String) extends StaticAnnotation

sealed trait Animal
@says("oof") case class Dog(name: String) extends Animal
@says("meow") case class Cat(name: String) extends Animal
```

TBD
