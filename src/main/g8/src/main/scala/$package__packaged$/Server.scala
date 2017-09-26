package $package$

import org.http4s.util.ProcessApp
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext
import scalaz.concurrent.Task
import scalaz.stream.Process

object BlazeExample extends ProcessApp {
  override def process(args: List[String]): Process[Task, Nothing] =
    BlazeBuilder
      .bindHttp(8080, "0.0.0.0")
      .mountService(HelloWorld.service)
      .serve
}
