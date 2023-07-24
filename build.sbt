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
  JavaSpec.temurin("8"),
  JavaSpec.temurin("11"),
  JavaSpec.temurin("17"),
  JavaSpec.graalvm("11")
)
ThisBuild / githubWorkflowPublishTargetBranches := Seq.empty

lazy val root = project
  .in(file("."))
  .settings(
    name := "http4s-g8",
    test in Test := {
      val _ = (g8Test in Test).toTask("").value
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
