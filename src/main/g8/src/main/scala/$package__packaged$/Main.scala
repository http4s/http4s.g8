package $package$

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp.Simple:
  def run: IO[Unit] =
    $name;format="Camel"$Server.stream[IO].compile.drain.as(ExitCode.Success)

