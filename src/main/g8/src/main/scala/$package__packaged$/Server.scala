package $package$

import java.util.concurrent.{ExecutorService, Executors}

import scala.util.Properties.envOrNone

import fs2.{Task, Stream}

import org.http4s.server.Server
import org.http4s.util.StreamApp
import org.http4s.server.blaze.BlazeBuilder


object BlazeExample extends StreamApp {

  val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip   : String           = "0.0.0.0"
  val pool : ExecutorService  = Executors.newCachedThreadPool()

  override def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(HelloWorld.service)
      .withServiceExecutor(pool)
      .serve
}
