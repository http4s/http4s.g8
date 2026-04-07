import scala.sys.process._

// This build is for this Giter8 template.
// To test the template run `g8` or `g8Test` from the sbt session.
// See http://www.foundweekends.org/giter8/testing.html#Using+the+Giter8Plugin for more details.

ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(List("g8TestMill"), name = Some("Test generated template")),
)

ThisBuild / githubWorkflowEnv := Map(
  "GITHUB_TOKEN" -> "${{ secrets.GITHUB_TOKEN }}", 
  "XDG_CACHE_HOME" -> "${{ github.workspace }}"
)

val PrimaryOS = "ubuntu-latest"
val MacOS = "macos-latest"
ThisBuild / githubWorkflowOSes := Seq(PrimaryOS, MacOS)
ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.temurin("11"),
  JavaSpec.temurin("17"),
)
ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty

val Http4sVersion = "0.23.33"
val CirceVersion = "0.14.15"
val MunitVersion = "1.2.4"
val LogbackVersion = "1.5.32"
val MunitCatsEffectVersion = "2.2.0"

val g8TestMill = taskKey[Unit]("Test generated template for Mill")

lazy val root = project
  .in(file("."))
  .settings(
    name := "http4s-g8",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect"   % MunitCatsEffectVersion % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion         % Runtime,
    ),
    addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.3"),
    addSbtPlugin("io.spray" % "sbt-revolver" % "0.10.0"),
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.3.1"),
    Test / g8TestMill := {
      // TODO: reenable this
      // val exitCode = ("./mill validate" !)
      // if (exitCode != 0) {
      //   throw new RuntimeException("failed template verification")
      // } 
    },
    Test / test := {
      val _ = (Test / g8TestMill).toTask(_ => "").value
    },
    scriptedLaunchOpts ++= List(
      "-Xms1024m",
      "-Xmx1024m",
      "-XX:ReservedCodeCacheSize=128m",
      "-Xss2m",
      "-Dfile.encoding=UTF-8"
    ),
    resolvers += Resolver.url(
      "typesafe",
      url("https://repo.typesafe.com/typesafe/ivy-releases/")
    )(Resolver.ivyStylePatterns),
  )
