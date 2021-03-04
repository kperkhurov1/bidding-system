package com.anderson.biddingSystem.dataSource

object Model {

  case class Targeting(targetedSiteIds: Set[String])

  case class Banner(id: Int,
                    src: String,
                    width: Int,
                    height: Int)

  case class Campaign(id: Int,
                      country: String,
                      targeting: Targeting,
                      banners: List[Banner],
                      bid: Double)
}
