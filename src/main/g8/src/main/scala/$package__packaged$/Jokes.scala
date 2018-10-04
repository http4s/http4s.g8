package $package$

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import io.circe.{Encoder, Decoder, Json, HCursor}
import io.circe.generic.semiauto._
import org.http4s.{EntityDecoder, EntityEncoder, Method, Uri, Request}
import org.http4s.client.Client
import org.http4s.circe._

trait Jokes[F[_]]{
  def get: F[Jokes.Joke]
}

object Jokes {
  final case class Joke(joke: String) extends AnyVal
  object Joke {
    // We can make this easier if we use circe-generic
    implicit val jokeDecoder: Decoder[Joke] = deriveDecoder[Joke]
    implicit def jokeEntityDecoder[F[_]: Sync]: EntityDecoder[F, Joke] = 
      jsonOf

    implicit val jokeEncoder: Encoder[Joke] = deriveEncoder[Joke]
    implicit def jokeEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Joke] =
      jsonEncoderOf
  }

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl[F[_]: Sync](C: Client[F]): Jokes[F] = new Jokes[F]{
    def get: F[Jokes.Joke] = {
      val request = Request[F](
        Method.GET,
        Uri.uri("https://icanhazdadjoke.com/")
      )
      C.expect[Joke](request)
        .adaptError{ case t => JokeError(t)} // Prevent Client Json Decoding Failure Leaking
    }
  }

}