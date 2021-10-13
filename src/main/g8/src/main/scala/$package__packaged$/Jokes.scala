package $package$

import cats.effect.IO
import cats.implicits._
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}
import org.http4s.Method._
import org.http4s._
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.client.dsl.io._
import org.http4s.implicits._

trait Jokes{
  def get: IO[Jokes.Joke]
}

object Jokes {
  final case class Joke(joke: String) extends AnyVal
  object Joke {
    implicit val jokeDecoder: Decoder[Joke] = deriveDecoder[Joke]
    implicit val jokeEntityDecoder: EntityDecoder[IO, Joke] = jsonOf
    implicit val jokeEncoder: Encoder[Joke] = deriveEncoder[Joke]
    implicit val jokeEntityEncoder: EntityEncoder[IO, Joke] = jsonEncoderOf
  }

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl(C: Client[IO]): Jokes = new Jokes{
    def get: IO[Jokes.Joke] = {
      C.expect[Joke](GET(uri"https://icanhazdadjoke.com/"))
        .adaptError{ case t => JokeError(t)} // Prevent Client Json Decoding Failure Leaking
    }
  }
}
