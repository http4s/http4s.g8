package $package$

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp.Simple:
  val run = $name;format="Camel"$Server.run[IO]
