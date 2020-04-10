package fetcher

import java.net.{SocketTimeoutException, URL}

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.concurrent.{ExecutionContext, Future}
import response.{FailedResponse, Response, SuccessfulResponse}
import response.error.{InvalidURLError, TimeoutError}


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
      case _: IllegalArgumentException => FailedResponse(InvalidURLError(url)) // Do we need to check anything else from the exception.
      case _: SocketTimeoutException => FailedResponse(TimeoutError(timeout))
      // Should we catch other cases?
    }
  }
}
