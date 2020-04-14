import response.SuccessfulResponse
import scrapper.UrlScrapper

import scala.concurrent.Await
import scala.io.StdIn.readLine
import scala.concurrent.duration._
import scala.language.postfixOps

object TestApp extends App {
  while(true) {
    println("Enter a url: ")
    val url = readLine()
    val parser = new UrlScrapper()
    val result = Await.result(parser.scrap(url), 5 seconds)
    result match {
      case SuccessfulResponse(res) => println(res)
      case _ => "Json-ld could not be generated."
    }
  }
}
