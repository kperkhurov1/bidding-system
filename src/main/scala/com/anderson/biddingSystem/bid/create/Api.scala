package com.anderson.biddingSystem.bid.create

object ApiRequest {

  case class Geo(country: Option[String])

  case class BidUser(id: String, geo: Option[Geo])

  case class Device(id: String, geo: Option[Geo])

  case class Impression(id: String,
                        wmin: Option[Int],
                        wmax: Option[Int],
                        w: Option[Int],
                        hmin: Option[Int],
                        hmax: Option[Int],
                        h: Option[Int],
                        bidFloor: Option[Double])

  case class Site(id: String, domain: String)

  case class BidRequest(id: String,
                        imp: Option[List[Impression]],
                        site: Site,
                        user: Option[BidUser],
                        device: Option[Device])
}

object ApiResponse {

  case class Banner(id: Int,
                    src: String,
                    width: Int,
                    height: Int)

  case class BidResponse(id: String,
                         bidRequestId: String,
                         price: Double,
                         adId: Option[String],
                         banner: Option[Banner])
}
