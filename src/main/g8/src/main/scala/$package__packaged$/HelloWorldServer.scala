package $package$

import cats.effect.{ConcurrentEffect, Effect, ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.server.blaze.BlazeBuilder

object HelloWorldServer extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    ServerStream.stream[IO].compile.drain.as(ExitCode.Success)
}

object ServerStream {
  def helloWorldRoutes[F[_]: Effect]: HttpRoutes[F] = new HelloWorldRoutes[F].routes

  def stream[F[_]: ConcurrentEffect]: fs2.Stream[F, ExitCode]=
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldRoutes, "/")
      .serve
}
