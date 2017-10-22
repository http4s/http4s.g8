package $package$

import org.http4s.dsl._
import org.http4s.{Method, Request, Response, Status}
import org.specs2.Specification
import org.specs2.matcher.MatchResult
import org.specs2.specification.core.SpecStructure

class HelloWorldSpec extends Specification {
  def is: SpecStructure = {
    s2"""
  This is a specification to check the 'HelloWorldSpec' Endpoint

  The 'HelloWorldSpec' endpoint should
    route returns 200  $e1
    route returns hello world $e2
  """
  }

  def e1: MatchResult[Status] = routeReturns200()
  def e2: MatchResult[String] = routeReturnsHelloWorld()


  val retHelloWorld: Response = {
    val getHW = Request(Method.GET, uri("/hello/world"))
    val task = HelloWorld.service.run(getHW)
    task.unsafeRun.toOption.get
  }

  def routeReturns200(): MatchResult[Status] = {
    retHelloWorld.status === Status.Ok
  }

  def routeReturnsHelloWorld(): MatchResult[String] = {
    retHelloWorld.as[String].unsafeRun() === "Hello, world"
  }
}

