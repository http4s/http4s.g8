package $package$

import fs2.{Task, Stream}
import java.util.concurrent.{ExecutorService, Executors}
import org.http4s.util.StreamApp
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext
import scala.util.Properties.envOrNone

object BlazeExample extends StreamApp {

  val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip   : String           = "0.0.0.0"
  val pool : ExecutorService  = Executors.newCachedThreadPool()

  override def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(HelloWorld.service)
      .withExecutionContext(ExecutionContext.fromExecutorService(pool))
      .serve
}
