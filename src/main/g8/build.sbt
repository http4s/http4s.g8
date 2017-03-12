organization := "$organization$"
name := "$name;format="norm"$"
version := "0.0.1-SNAPSHOT"
scalaVersion := "$scala_version$"

val Http4sVersion = "$http4s_version$"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe"        % Http4sVersion,
  "org.http4s" %% "http4s-dsl"          % Http4sVersion
)

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"
