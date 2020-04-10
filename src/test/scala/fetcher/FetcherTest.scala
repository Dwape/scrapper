package fetcher

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import response.error.{InvalidURLError, TimeoutError}
import response.{FailedResponse, SuccessfulResponse}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class FetcherTest extends AnyFlatSpec with Matchers {

  "Fetcher" should "get the contents of a site" in {
    val fetcher = new UrlFetcher()

    val result = Await.result(fetcher.fetch("https://google.com"), 5 seconds)
    result shouldBe a[SuccessfulResponse[_]]
    result.isSuccess should be(true)
    // Should we check that it actually has content?
  }

  "Fetcher" should "fail when the url is invalid" in {
    val fetcher = new UrlFetcher()

    val result = Await.result(fetcher.fetch("htps://google.com"), 5 seconds)
    result shouldBe a[FailedResponse[_]]
    result match {
      case FailedResponse(error) =>
        error shouldBe a[InvalidURLError]
      case _ =>
    }
  }

  "Fetcher" should "fail when the request takes too long" in {
    val fetcher = new UrlFetcher()

    val result = Await.result(fetcher.fetch("https://google.com", 1), 5 seconds)
    result shouldBe a[FailedResponse[_]]
    result match {
      case FailedResponse(error) =>
        error shouldBe a[TimeoutError]
      case _ =>
    }
  }
}
