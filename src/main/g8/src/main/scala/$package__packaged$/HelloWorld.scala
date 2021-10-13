package $package$

import cats.effect.IO
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait HelloWorld{
  def hello(n: HelloWorld.Name): IO[HelloWorld.Greeting]
}

object HelloWorld {
  final case class Name(name: String) extends AnyVal
  /**
   * More generally you will want to decouple your edge representations from
   * your internal data structures, however this shows how you can
   * create encoders for your data.
   **/
  final case class Greeting(greeting: String) extends AnyVal
  object Greeting {
    implicit val greetingEncoder: Encoder[Greeting] = new Encoder[Greeting] {
      final def apply(a: Greeting): Json = Json.obj(
        ("message", Json.fromString(a.greeting)),
      )
    }
    implicit val greetingEntityEncoder: EntityEncoder[IO, Greeting] =
      jsonEncoderOf[IO, Greeting]
  }

  def impl: HelloWorld = new HelloWorld{
    def hello(n: HelloWorld.Name): IO[HelloWorld.Greeting] =
      Greeting("Hello, " + n.name).pure[IO]
  }
}
