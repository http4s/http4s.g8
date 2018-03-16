package $package$

import cats.effect.{Effect, IO}
import fs2.StreamApp
import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext.Implicits.global

object HelloWorldServerApp extends HelloWorldServer[IO]

abstract class HelloWorldServer[F[_]: Effect] extends StreamApp[F] with Http4sDsl[F] {
  val service = HttpService[F] {
    case GET -> Root / "hello" / name =>
      Ok(Json.obj("message" -> Json.fromString(s"Hello, \${name}")))
  }

  def stream(args: List[String], requestShutdown: F[Unit]) =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(service, "/")
      .serve
}
