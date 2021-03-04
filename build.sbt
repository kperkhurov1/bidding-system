import Dependencies._

lazy val backend = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    organization := "com.anderson",
    version := "0.1.0",
    scalaVersion := "2.13.1",
    name := "bindingSystem",
    libraryDependencies ++= Seq(
      scalaTest,
      akkaHttp,
      akkaTyped,
      akkaStream,
      jsonSpray,
      cats,
      log4s,
      logback,
      logstashLogbackEncoder,
      scalaLogging
    )
  )

mainClass in (Compile, run) := Some("com.anderson.biddingSystem.Main")
