package $package$

import cats.effect.{Async, Concurrent, ContextShift, Resource, Timer}
import cats.syntax.all._
import fs2.Stream
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object $name;format="Camel"$Server {

  def stream[F[_]: Concurrent: ContextShift: Timer]: Stream[F, Nothing] = {
    for {
      client <- Stream.resource(EmberClientBuilder.default[F].build)
      helloWorldAlg = HelloWorld.impl[F]
      jokeAlg = Jokes.impl[F](client)

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.
      httpApp = (
        $name;format="Camel"$Routes.helloWorldRoutes[F](helloWorldAlg) <+>
        $name;format="Camel"$Routes.jokeRoutes[F](jokeAlg)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(true, true)(httpApp)

      exitCode <- Stream.resource(
        EmberServerBuilder.default[F]
          .withHost("0.0.0.0")
          .withPort(8080)
          .withHttpApp(finalHttpApp)
          .build >>
        Resource.eval(Async[F].never)
      )
    } yield exitCode
  }.drain
}
