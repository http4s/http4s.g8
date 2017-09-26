package $package$

import cats.effect.IO
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.{StreamApp, _}

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._

import scala.util.Properties.envOrNone

object BlazeExample extends StreamApp[IO] {
  val route = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(Json.obj("message" -> Json.fromString(s"Hello, \${name}")))
  }

  def stream(args: List[String], requestShutdown: IO[Unit]) = {
    val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
    val ip   : String           = "0.0.0.0"

    BlazeBuilder[IO]
      .bindHttp(port, ip)
      .mountService(route,"/")
      .serve
  }
}