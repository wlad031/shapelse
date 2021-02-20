# Shapelse

[![ci](https://img.shields.io/github/workflow/status/wlad031/shapelse/Scala%20CI?label=CI&logo=GitHub&style=flat-square)](https://github.com/wlad031/shapelse/actions)
[![codecov](https://img.shields.io/codecov/c/github/wlad031/shapelse?label=cov&logo=Codecov&style=flat-square)](https://codecov.io/gh/wlad031/shapelse)

Shapelse (_shape_ + _else_) is a small abstraction layer on top of [Shapeless](https://github.com/milessabin/shapeless). As an abstraction layer it has less freedom in usage but in some cases it's more convenient to use.

Shapelse allows deriving strictly defined *Schema*, or *Field*, for your ADTs ([what are ADTs?](https://alvinalexander.com/scala/fp-book/algebraic-data-types-adts-in-scala/)).