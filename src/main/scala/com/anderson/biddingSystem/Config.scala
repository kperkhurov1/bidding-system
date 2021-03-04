package com.anderson.biddingSystem

import com.typesafe.config.{Config, ConfigFactory}

object Config {

  def httpConfigFactory(configName: String): HttpConfig = {
    val config = ConfigFactory.load.getConfig(configName)
    val host = config.optionalString("host").getOrElse("localhost")
    val port = config.optionalInt("port").getOrElse(8080)

    HttpConfig(host, port)
  }

  implicit class RichConfig(val config: Config) extends AnyVal {
    def optionalString(path: String): Option[String] = if (config.hasPath(path)) {
      Some(config.getString(path))
    } else {
      None
    }

    def optionalInt(path: String): Option[Int] = if (config.hasPath(path)) {
      Some(config.getInt(path))
    } else {
      None
    }
  }

  case class HttpConfig(val host: String, val port: Int)
}
