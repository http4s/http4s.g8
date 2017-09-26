package $package$

import fs2.{Task, Stream}
import org.http4s.util.StreamApp
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeBuilder
import scala.concurrent.ExecutionContext

object BlazeExample extends StreamApp {
  override def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(8080, "0.0.0.0")
      .mountService(HelloWorld.service)
      .serve
}
