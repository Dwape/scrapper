package scrapper

import response.Response

import scala.concurrent.Future

trait Scrapper {
  def scrap(url: String): Future[Response[String]] // What should the result be?
}
