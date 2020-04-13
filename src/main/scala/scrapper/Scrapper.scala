package scrapper

import response.Response

import scala.concurrent.Future

/**
 * Scraps a website, returning a json-ld describing the Things found on the page.
 * Definitions for all Things are according to http://schema.org
 */
trait Scrapper {

  /**
   * Scraps a single url, returning its corresponding json-ld once the website has been parsed.
   * @param url The url of the website to be scrapped.
   * @return A Response that contains the json-ld if it was successful.
   */
  def scrap(url: String): Future[Response[String]]

  /**
   * Scaps several urls, returning the json-lds for all of them once the last one has been parsed.
   * @param urls A list containing all the urls to be parsed.
   * @return A Response that will contain all the json-lds if all the pages could be parsed successfully.
   */
  def batchScrap(urls: List[String]): Future[Response[List[String]]]

  // Add a batch scrap with streams.
}
