package com.anderson.biddingSystem.bid.create

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.anderson.biddingSystem.bid.create.ApiRequest.{BidRequest, BidUser, Device, Geo, Impression, Site}
import com.anderson.biddingSystem.bid.create.ApiResponse.{Banner, BidResponse}
import spray.json.{DefaultJsonProtocol, PrettyPrinter}

trait JsonTransformer extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val printer = PrettyPrinter
  implicit val geoJsonFormat = jsonFormat1(Geo)
  implicit val bidUserJsonFormat = jsonFormat2(BidUser)
  implicit val siteJsonFormat = jsonFormat2(Site)
  implicit val deviceJsonFormat = jsonFormat2(Device)
  implicit val impressionJsonFormat = jsonFormat8(Impression)
  implicit val bidRequestJsonFormat = jsonFormat5(BidRequest)
  implicit val bannerJsonFormat = jsonFormat4(Banner)
  implicit val bidResponseJsonFormat = jsonFormat5(BidResponse)
}
