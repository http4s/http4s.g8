package $package$

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import io.circe.{Encoder, Decoder, Json, HCursor}
import org.http4s.{EntityDecoder, EntityEncoder, Method, Uri, Request}
import org.http4s.client.Client
import org.http4s.circe._

trait JokeAlg[F[_]]{
  def getJoke: F[JokeAlg.Joke]
}

object JokeAlg {
  final case class Joke(joke: String) extends AnyVal
  object Joke {
    // We can make this easier if we use circe-generic
    implicit val jokeDecoder: Decoder[Joke] = new Decoder[Joke]{
      final def apply(c: HCursor): Decoder.Result[Joke] =
        c.downField("joke").as[String].map(Joke(_))
    }
    implicit def jokeEntityDecoder[F[_]: Sync]: EntityDecoder[F, Joke] = 
      jsonOf

    implicit val jokeEncoder: Encoder[Joke] = new Encoder[Joke] {
      final def apply(a: Joke): Json = Json.obj(
        ("joke", Json.fromString(a.joke)),
      )
    }
    implicit def jokeEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Joke] =
      jsonEncoderOf
  }

  final case class JokeAlgError(e: Throwable) extends RuntimeException

  def impl[F[_]: Sync](C: Client[F]): JokeAlg[F] = new JokeAlg[F]{
    def getJoke: F[JokeAlg.Joke] = {
      val request = Request[F](
        Method.GET,
        Uri.uri("https://icanhazdadjoke.com/")
      )
      C.expect[Joke](request)
        .adaptError{ case t => JokeAlgError(t)} // Prevent Client Json Decoding Failure Leaking
    }
  }

}