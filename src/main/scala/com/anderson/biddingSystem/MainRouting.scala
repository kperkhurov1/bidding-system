package com.anderson.biddingSystem

import akka.http.scaladsl.server.Directives._
import com.anderson.biddingSystem.bid.create.Routing

object MainRouting {

  lazy val routes = Seq(
    new Routing().route
  ).reduce(_ ~ _)
}
