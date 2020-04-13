package fetcher

import java.net.{SocketTimeoutException, URL}

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.concurrent.{ExecutionContext, Future}
import response.{FailedResponse, Response, SuccessfulResponse}
import response.error.{InvalidURLError, TimeoutError, UnexpectedError}

/**
 * An implementation of the Fetcher trait.
 */
class UrlFetcher extends Fetcher {

  import ExecutionContext.Implicits.global // Check what to do with the execution context.

  /**
   * Fetches and parses the contents of the website with the url provided.
   * @param url The url of the site.
   * @param timeout The time (in milliseconds) after which the request times out
   * @return A FetcherResponse which will be successful if the contents could be fetched.
   */
  override def fetch(url: String, timeout: Int = 3000): Future[Response[Document]] = {
    Future {
      Jsoup.connect(url)
        .timeout(timeout)
        .get()
    }.map {
      document => SuccessfulResponse(document)
    }.recover {
      case _: IllegalArgumentException => FailedResponse(InvalidURLError(url))
      case _: SocketTimeoutException => FailedResponse(TimeoutError(timeout))
      case e: Throwable => FailedResponse(UnexpectedError(e))
      // Should we catch other cases?
    }
  }
}
