package $package$

import java.util.concurrent.{ExecutorService, Executors}

import fs2.Task
import scala.util.Properties.envOrNone
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext


object BlazeExample extends ServerApp {

  val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip   : String           = "0.0.0.0"
  val pool : ExecutorService  = Executors.newCachedThreadPool()

  override def server(args: List[String]): Task[Server] =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(HelloWorld.service)
      .withExecutionContext(ExecutionContext.fromExecutorService(pool))
      .start
}
