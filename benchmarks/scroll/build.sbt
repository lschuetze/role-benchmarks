lazy val commonSettings = Seq(
  organization := "tu.dresden.de",
  name := "benchmark-scroll",
  version := "0.0.1",
  scalaVersion := "3.3.1",
  scalacOptions ++= Seq(
  	"-encoding", "utf8",
    "-deprecation",
    "-feature",
    "-language:dynamics",
    "-language:reflectiveCalls",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-unchecked",
    "-target:jvm-1.8"),
  fork := true,
  Compile / javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
)

lazy val scroll = ProjectRef(uri("https://github.com/max-leuthaeuser/SCROLL.git#master"), "core")

lazy val main = (project in file("."))
  //.configs(Profile, Graal)
  .dependsOn(scroll)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.17",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test",
    Compile / run / javaOptions ++= Seq("--add-opens", "java.base/java.lang=ALL-UNNAMED")
  )
