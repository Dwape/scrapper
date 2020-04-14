package scrapper
import fetcher.UrlFetcher
import org.jsoup.nodes.Document
import registry.UrlRegistry
import response.error.{InvalidURLError, NoParserFoundError}
import response.{FailedResponse, Response, SuccessfulResponse}
import search.FinderBuilder
import structure.ObjectValue

import scala.concurrent.{ExecutionContext, Future}

/**
 * An implementation of the Scrapper trait.
 */
class UrlScrapper extends Scrapper {

  import ExecutionContext.Implicits.global

  /**
   * Scraps a single url, returning its corresponding json-ld once the website has been parsed.
   * @param url The url of the website to be scrapped.
   * @return A Response that contains the json-ld if it was successful.
   */
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
      case None => Future.successful(FailedResponse[String](NoParserFoundError(url)))
    }
  }

  /**
   * Scraps several urls, returning the json-lds for all of them once the last one has been parsed.
   * @param urls A list containing all the urls to be parsed.
   * @return A Response that will contain all the json-lds if all the pages could be parsed successfully.
   */
    // This would make more sense if it was done with a stream and the json-lds were streamed as soon as they've been parsed.
  override def batchScrap(urls: List[String]): Future[Response[List[String]]] = {
    if (urls.isEmpty) Future.successful(FailedResponse(NoParserFoundError(urls.head)))
    val registry = new UrlRegistry()
    registry.findParser(urls.head) match {
      case Some(parser) =>
        val fetcher = new UrlFetcher()
        Future.sequence(urls.map {
          url => fetcher.fetch(url).map {
            case SuccessfulResponse(document) =>
              parser.parse(document)
          }
        }).map(list => SuccessfulResponse(list))
      case None => Future.successful(FailedResponse(NoParserFoundError(urls.head)))
    }
  }
}
