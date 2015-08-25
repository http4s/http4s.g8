organization := "$organization$"
name := "$name;format="norm"$"
version := "0.0.1-SNAPSHOT"
scalaVersion := "$scala_version$"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % "$http4s_version$",
  "org.http4s" %% "http4s-dsl"          % "$http4s_version$",
  "org.http4s" %% "http4s-argonaut"     % "$http4s_version$"
)

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"
