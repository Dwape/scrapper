package fetcher

import scala.concurrent.Future
import org.jsoup.nodes.Document
import response.Response

/**
 * Fetches the contents of a website.
 */
trait Fetcher {

  /**
   * Fetches and parses the contents of the website with the url provided.
   * @param url The url of the site.
   * @param timeout The time (in milliseconds) after which the request times out
   * @return A FetcherResponse which will be successful if the contents could be fetched.
   */
  def fetch(url: String, timeout: Int = 3000): Future[Response[Document]]
}
