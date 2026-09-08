val Http4sVersion = "0.23.37"
val CirceVersion = "0.14.16"
val MunitVersion = "1.3.6"
val LogbackVersion = "1.6.3"
val MunitCatsEffectVersion = "2.2.0"

lazy val root = (project in file("."))
  .settings(
    organization := "$organization$",
    name := "$name;format="norm"$",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.18",
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
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.4" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )
