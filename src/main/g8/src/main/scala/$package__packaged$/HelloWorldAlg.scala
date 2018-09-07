package $package$

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Decoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait HelloWorldAlg[F[_]]{
  def hello(n: HelloWorldAlg.Name): F[HelloWorldAlg.Greeting]
}

object HelloWorldAlg {
  final case class Name(name: String) extends AnyVal
  /**
    * More Generally You will want to decouple your edge representations from
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
    implicit def greetingEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Greeting] = 
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_]: Applicative]: HelloWorldAlg[F] = new HelloWorldAlg[F]{
    def hello(n: HelloWorldAlg.Name): F[HelloWorldAlg.Greeting] = 
        Greeting("Hello, " + n.name).pure[F]
  }
}