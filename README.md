# http4s giter8 template

Generate an http4s (Scala 2) service on the blaze backend with Circe.

1. [Install sbt](http://www.scala-sbt.org/1.0/docs/Setup.html)
2. `sbt new http4s/http4s.g8`
3. `cd quickstart`
4. `sbt run`
5. `curl http://localhost:8080/hello/$USER`
6. [Learn more](http://http4s.org/)

NOTE: to generate a Scala 3 service, use the `0.23-scala3` branch by replacing step 2. (`sbt new http4s/http4s.g8`) with `sbt new http4s/http4s.g8 --branch 0.23-scala3`
