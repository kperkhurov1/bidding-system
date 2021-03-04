package com.anderson.biddingSystem.bid.create

import akka.actor.typed.scaladsl.AskPattern.{Askable, schedulerFromActorSystem}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.util.Timeout
import com.anderson.biddingSystem.bid.create.ApiRequest.BidRequest
import com.anderson.biddingSystem.bid.create.ApiResponse.BidResponse
import com.anderson.biddingSystem.bid.create.BidActor.ProcessBid
import com.anderson.biddingSystem.dataSource.CampaignRepository

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

object RootActor {

  implicit val system = ActorSystem(BidActor(factory()), "BidActor")

  private implicit val timeout: Timeout = Timeout(3.seconds)

  def ask(bidRequest: BidRequest): Future[Option[BidResponse]] = system.ask(ProcessBid(bidRequest, _))

  private def factory(): Service = Service(CampaignRepository())
}

object BidActor {

  final case class ProcessBid(bidRequest: BidRequest, replyTo: ActorRef[Option[BidResponse]])

  def apply(service: Service): Behavior[ProcessBid] =
    Behaviors.receiveMessage { message =>
      val response = service.handle(message.bidRequest)
      message.replyTo ! response
      Behaviors.same
    }
}

