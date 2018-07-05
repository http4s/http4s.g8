package $package$

import cats.effect.{ConcurrentEffect, Effect, ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.server.blaze.BlazeBuilder

object HelloWorldServer extends IOApp {
  def run(args: List[String]) =
    ServerStream.stream[IO].compile.drain.as(ExitCode.Success)
}

object ServerStream {
  def helloWorldService[F[_]: Effect] = new HelloWorldService[F].service

  def stream[F[_]: ConcurrentEffect] =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldService, "/")
      .serve
}
