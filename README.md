# http4s giter8 template

This template is based on a final tagless architecture.  For an alternate version fixed on `cats.effect.IO`, see [http4s-io.g8](https://github.com/http4s/http4s-io.g8).

## Instructions

Generate an http4s service on the ember backend with Circe.

1. [Install mill](https://mill-build.org/mill/cli/installation-ide.html)
2. Create your project:
   - Scala 2: `mill -i init http4s/http4s.g8 --branch 0.23-mill`
3. `cd quickstart`
4. `mill run`
5. `curl http://localhost:8080/hello/$USER`
6. [Learn more](http://http4s.org/)

