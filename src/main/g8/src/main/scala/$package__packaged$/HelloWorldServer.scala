package $package$

import cats.effect.{Effect, IO}
import fs2.StreamApp
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorldServerApp extends HelloWorldServer[IO]

abstract class HelloWorldServer[F[_]: Effect] extends StreamApp[F] {

  def stream(args: List[String], requestShutdown: F[Unit]) =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(HelloWorldService.service, "/")
      .serve
}
