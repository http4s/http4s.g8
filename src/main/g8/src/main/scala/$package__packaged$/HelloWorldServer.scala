package $package$

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object HelloWorldServer extends IOApp {
  def run(args: List[String]) =
    ServerStream.stream[IO].compile.drain.as(ExitCode.Success)
}
