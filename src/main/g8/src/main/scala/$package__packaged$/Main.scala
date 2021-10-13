package $package$

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  def run:IO[Unit] = $name;format="Camel"$Server.run
}
