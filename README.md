# http4s giter8 template [![Build Status](https://travis-ci.org/http4s/http4s.g8.svg)](https://travis-ci.org/http4s/http4s.g8)

This template is based on a final tagless architecture.  For an alternate version fixed on `cats.effect.IO`, see [http4s-io.g8](https://github.com/http4s/http4s-io.g8).

## Instructions

Generate an http4s service on the blaze backend with Circe.

1. [Install sbt](http://www.scala-sbt.org/1.0/docs/Setup.html)
2. `sbt new http4s/http4s.g8`
3. `cd quickstart`
4. `sbt run`
5. `curl http://localhost:8080/hello/$USER`
6. [Learn more](http://http4s.org/)


Note:

We have enabled [sbt-partial-unification](https://github.com/fiadliel/sbt-partial-unification) 
necessary for utilizing all features of Http4s and Cats.
As well as [sbt-revolver](https://github.com/spray/sbt-revolver) for easier project reloading.

We have commented out the [sbt-tpolecat](https://github.com/DavidGregory084/sbt-tpolecat) plugin in the generated `project/plugins.sbt`.
We highly recommend it for projects, but it may not be a good baseline for new users.
