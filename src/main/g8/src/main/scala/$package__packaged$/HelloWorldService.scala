package $package$

import cats.{Applicative, Monad}
import io.circe.Json
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

object HelloWorldService {

  def service[F[_]: Applicative: Monad]: HttpService[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._

    HttpService[F] {
      case GET -> Root / "hello" / name =>
        Ok(Json.obj("message" -> Json.fromString(s"Hello, \${name}")))
    }
  }
}
