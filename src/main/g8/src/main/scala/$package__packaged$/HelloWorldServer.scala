package $package$

import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._

object HelloWorldServer extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    ServerStream.stream[IO].compile.drain.as(ExitCode.Success)
}

object ServerStream {
  def helloWorldRoutes[F[_]: Effect]: HttpRoutes[F] = new HelloWorldRoutes[F].routes

  def stream[F[_]: ConcurrentEffect: Timer]: fs2.Stream[F, ExitCode]=
    BlazeServerBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(Router(
        "/" -> helloWorldRoutes
      ).orNotFound)
      .serve
}
