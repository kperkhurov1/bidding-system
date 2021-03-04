import sbt._

object Dependencies {

  object Version {

    val circeVersion = "0.12.3"
  }

  val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2"

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.2.4"

  val akkaTyped = "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8"

  val jsonSpray = "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4"

  val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.6.8"

  val cats = "org.typelevel" %% "cats-core" % "2.1.1"

  val log4s = "org.log4s" %% "log4s" % "1.8.2"

  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"

  val logstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % "6.3"

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
}
