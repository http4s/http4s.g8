organization := "$organization$"
name := "$name;format="norm"$"
version := "0.0.1-SNAPSHOT"
scalaVersion := "$scala_version$"

val Http4sVersion = "$http4s_version$"
val Specs2Version = "$specs2_version$"
val LogbackVersion = "$logback_version$"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server"  % Http4sVersion,
  "org.http4s"     %% "http4s-circe"         % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"           % Http4sVersion,
  "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
  "org.specs2"     %% "specs2-mock"          % Specs2Version % "test",
  "ch.qos.logback" %  "logback-classic"      % LogbackVersion
)