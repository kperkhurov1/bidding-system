package com.anderson.biddingSystem.bid.create

import java.util.UUID

import cats.implicits.catsSyntaxOptionId
import com.anderson.biddingSystem.bid.create.ApiRequest.{BidRequest, Geo, Impression}
import com.anderson.biddingSystem.bid.create.ApiResponse.BidResponse
import com.anderson.biddingSystem.dataSource.Model.Campaign
import com.anderson.biddingSystem.dataSource.{CampaignRepository, Model}

trait ServiceType[R, F] {

  def handle(request: R): F
}

class Service(val campaignRepository: CampaignRepository) extends ServiceType[BidRequest, Option[BidResponse]] {

  override def handle(bidRequest: BidRequest): Option[BidResponse] = {
    val campaigns = campaignRepository.getCampaigns

    val result = getCountry(bidRequest).flatMap { country =>
      campaigns
        .filter(_.country.toLowerCase() == country)
        .filter(_.targeting.targetedSiteIds.contains(bidRequest.site.id))
        .flatMap(getBanner(bidRequest, _))
        .headOption
    }

    result.map { case (banner, price, campaign) =>
      BidResponse(
        UUID.randomUUID().toString,
        bidRequest.id,
        price,
        campaign.id.toString.some,
        banner.some.map(banner =>
          ApiResponse.Banner(
            id = banner.id,
            src = banner.src,
            width = banner.width,
            height = banner.height,
          )
        )
      )
    }
  }

  private def getCountry(bidRequest: BidRequest): Option[String] = {
    val f = (geo: Option[Geo]) => geo.flatMap(_.country.map(_.toLowerCase()))
    val deviceCountry = bidRequest.device.flatMap(d => f(d.geo))
    val userCountry = bidRequest.user.flatMap(u => f(u.geo))

    deviceCountry match {
      case Some(value) => Some(value)
      case None => userCountry
    }
  }

  private def getBanner(bidRequest: BidRequest, campaign: Campaign): Option[(Model.Banner, Double, Campaign)] = {
    bidRequest.imp.flatMap { _.map(getModelBanner(_, campaign)).find(_.isDefined) }.flatten
  }

  private def getModelBanner(imp: Impression, campaign: Campaign): Option[(Model.Banner, Double, Campaign)] = {
    campaign.banners.find(b =>
      isImpSizeValid(imp.w, imp.wmin, imp.wmax, b.width) &&
      isImpSizeValid(imp.h, imp.hmin, imp.hmax, b.height) &&
      imp.bidFloor.isDefined
    ).map((_, imp.bidFloor.get, campaign))
  }

  private def isImpSizeValid(
                              requestValue: Option[Int],
                              requestMinValue: Option[Int],
                              requestMaxValue: Option[Int],
                              bannerValue: Int): Boolean = {
    (requestValue, requestMinValue, requestMaxValue) match {
      case (Some(value), _, _) => value == bannerValue
      case (_, Some(minValue), Some(maxValue)) => minValue <= bannerValue && maxValue >= bannerValue
      case (_, Some(minValue), _) => minValue <= bannerValue
      case (_, _, Some(maxValue)) => maxValue >= bannerValue
      case _ => false
    }
  }
}

object Service {

  def apply(campaignRepository: CampaignRepository): Service = new Service(campaignRepository)
}
