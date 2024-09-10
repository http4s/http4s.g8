// This build is for this Giter8 template.
// To test the template run `g8` or `g8Test` from the sbt session.
// See http://www.foundweekends.org/giter8/testing.html#Using+the+Giter8Plugin for more details.

ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(List("g8Test"), name = Some("Test generated template")),
  WorkflowStep.Run(
    List(
      "cd target/sbt-test/http4s-g8/scripted",
      "sbt assembly",
      "gu install native-image",
      "cat native-image-readme.md | grep 'native-image  -H*' | sh"
    ),
    cond = Some("startsWith(matrix.java, 'graalvm')"),
    name = Some("Build native assembly")
  )
)

val PrimaryOS = "ubuntu-latest"
val MacOS = "macos-latest"
ThisBuild / githubWorkflowOSes := Seq(PrimaryOS, MacOS)
ThisBuild / githubWorkflowJavaVersions := Seq(
  JavaSpec.temurin("11"),
  JavaSpec.temurin("17"),
  JavaSpec.graalvm("17")
)
ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty

val Http4sVersion = "0.23.28"
val CirceVersion = "0.14.9"
val MunitVersion = "1.0.1"
val LogbackVersion = "1.5.7"
val MunitCatsEffectVersion = "2.0.0"

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
    addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.2"),
    addSbtPlugin("io.spray" % "sbt-revolver" % "0.10.0"),
    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.2.0"),
    Test / test := {
      val _ = (Test / g8Test).toTask("").value
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
