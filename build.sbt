val scala3Version = "3.7.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "adventofcode-2025",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.2.1" % Test,
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.2.0"
    )
  )
