package $package$

import org.http4s.dsl._
import org.http4s.{Method, Request, Response, Status}
import org.specs2._

class HelloWorldSpec extends Specification {
  "HelloWorld" should {
    "return 200" in {
      uriReturns200()
    }
    "return hellow world" in {
      uriReturnsHelloWorld()
    }
  }

  private[this] val retHelloWorld: Response = {
    val getHW = Request(Method.GET, uri("/hello/world"))
    val task = HelloWorld.service.run(getHW)
    task.unsafeRun.toOption.get
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retHelloWorld.status === Status.Ok

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retHelloWorld.as[String].unsafeRun() === "Hello, world"
}

