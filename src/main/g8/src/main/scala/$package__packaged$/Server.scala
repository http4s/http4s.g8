package $package$

import java.util.concurrent.{ExecutorService, Executors}

import scala.util.Properties.envOrNone

import fs2.Task

import org.http4s.server.Server
import org.http4s.util.StreamApp
import org.http4s.server.blaze.BlazeBuilder


object BlazeExample extends StreamApp {

  val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip   : String           = "0.0.0.0"
  val pool : ExecutorService  = Executors.newCachedThreadPool()

  def main(args: List[String]) =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(HelloWorld.service)
      .withServiceExecutor(pool)
      .serve
}
