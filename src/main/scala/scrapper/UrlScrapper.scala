package scrapper
import fetcher.UrlFetcher
import registry.UrlRegistry
import response.error.InvalidURLError
import response.{FailedResponse, Response, SuccessfulResponse}

import scala.concurrent.{ExecutionContext, Future}

class UrlScrapper extends Scrapper {

  import ExecutionContext.Implicits.global

  override def scrap(url: String): Future[Response[String]] = {
    val registry = new UrlRegistry()
    registry.findParser(url) match {
      case Some(parser) =>
        val fetcher = new UrlFetcher()
        fetcher.fetch(url).map {
          case SuccessfulResponse(document) =>
            SuccessfulResponse(parser.parse(document)) // Check if it can have an error.
          case FailedResponse(error) => FailedResponse(error)
        }
      case None => Future.successful(FailedResponse[String](InvalidURLError("failed"))) // Change for the actual error.
    }
  }
}
