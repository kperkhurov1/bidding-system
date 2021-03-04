package com.anderson.biddingSystem

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.typesafe.scalalogging.StrictLogging

import scala.io.StdIn
import scala.util.{Failure, Success}

object Main extends App with StrictLogging {

  implicit val system = ActorSystem(Behaviors.empty, "bidding-system")
  implicit val executionContext = system.executionContext

  val config = Config.httpConfigFactory("http")
  val server = Http().newServerAt(config.host, config.port).bind(MainRouting.routes)

  server.onComplete {
    case Success(binding) =>
      val address = binding.localAddress
      system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
    case Failure(ex) =>
      system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
      system.terminate()
  }

  StdIn.readLine()

  server
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
