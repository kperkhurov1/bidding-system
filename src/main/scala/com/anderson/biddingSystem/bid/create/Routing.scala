package com.anderson.biddingSystem.bid.create

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, entity, onSuccess, path, post}
import akka.http.scaladsl.server.{Directives, Route}
import com.anderson.biddingSystem.bid.create.ApiRequest._

class Routing() extends JsonTransformer  {

  def route: Route = {
    path("bids") {
      post {
        entity(Directives.as[BidRequest]) { bidRequest =>
          onSuccess(RootActor.ask(bidRequest)) {
            case Some(resp) => complete(StatusCodes.OK, resp)
            case None => complete(StatusCodes.NoContent)
          }
        }
      }
    }
  }
}
