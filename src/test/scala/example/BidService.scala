package example

import cats.implicits.catsSyntaxOptionId
import com.anderson.biddingSystem.bid.create.ApiRequest._
import com.anderson.biddingSystem.bid.create.ApiResponse.BidResponse
import com.anderson.biddingSystem.bid.create.Service
import com.anderson.biddingSystem.dataSource.CampaignRepository
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BidService extends AnyFlatSpec with Matchers {

  "The bid request" should "get successful bid response" in {
    val service = Service(CampaignRepository())
    val request = getRequest("0006a522ce0f4bbbbaa6b3c38cafaa0f", "LT".some, "LT".some, getImp(3.12123.some))
    val response = service.handle(request)

    succesfulAssert(response)
  }

  "The bid request with wrong country" should "get wrong response" in {
    val service = Service(CampaignRepository())
    val request = getRequest("0006a522ce0f4bbbbaa6b3c38cafaa0f", "RU".some, "RU".some, getImp(3.12123.some))
    val response = service.handle(request)

    assert(response === None)
  }

  "The bid request with wrong user country" should "get successful response" in {
    val service = Service(CampaignRepository())
    val request = getRequest("0006a522ce0f4bbbbaa6b3c38cafaa0f", "RU".some, "LT".some, getImp(3.12123.some))
    val response = service.handle(request)

    succesfulAssert(response)
  }

  "The bid request with missed device country" should "get successful response" in {
    val service = Service(CampaignRepository())
    val request = getRequest("0006a522ce0f4bbbbaa6b3c38cafaa0f", "LT".some, None, getImp(3.12123.some))
    val response = service.handle(request)

    succesfulAssert(response)
  }

  "The bid request with wrong side id" should "get wrong response" in {
    val service = Service(CampaignRepository())
    val request = getRequest("111", "LT".some, None, getImp(3.12123.some))
    val response = service.handle(request)

    assert(response === None)
  }

  "The bid request with missed bidFloor" should "get wrong response" in {
    val service = Service(CampaignRepository())
    val request = getRequest("111", "LT".some, None, getImp(None))
    val response = service.handle(request)

    assert(response === None)
  }

  private def getRequest(
                          siteId: String,
                          userCountry: Option[String],
                          deviceCountry: Option[String],
                          imp: Impression
                        ) = {
    BidRequest(
      id = "SGu1Jpq1IO",
      imp = List(imp).some,
      site = Site(id = siteId, domain = "fake.tld"),
      user = BidUser("USARIO1", Geo(userCountry).some).some,
      device = Device("440579f4b408831516ebd02f6e1c31b4", Geo(deviceCountry).some).some
    )
  }

  private def getImp(bidFloor: Option[Double]) = {
    Impression(
      id = "1",
      wmin = 50.some,
      wmax = 300.some,
      hmin = 100.some,
      hmax = 300.some,
      h = 250.some,
      w = 300.some,
      bidFloor = bidFloor
    )
  }

  private def succesfulAssert(response: Option[BidResponse]) = {
    assert(response.map(_.bidRequestId) === "SGu1Jpq1IO".some)
    assert(response.map(_.price) === 3.12123.some)
    assert(response.flatMap(_.adId) === "3".some)
    assert(response.flatMap(_.banner.map(_.id)) === 6.some)
  }
}
