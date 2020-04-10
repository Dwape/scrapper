package structure

import fetcher.UrlFetcher
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import parser.{ParserBuilder, SearchNode}
import response.{Response, SuccessfulResponse}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

// Change name
class StructureTest extends AnyFlatSpec with Matchers {

  "Values" should "be parsable" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a id=\"link\" href=\"http://schema.org\"></a></body></html>")
    val parser = ParserBuilder().id("link").selectAttribute("href")
    val result = RootValue(ObjectValue(List(Property("name", StringValue("Tom")), Property("value", FetchedValue(parser)))))
    println(result.parse())
  }

  "Test" should "be able to fetch a real site" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = ParserBuilder().classes(List("titulo")).selectText()
        val res = RootValue(ObjectValue(List(Property("title", FetchedValue(parser)))))
        println(res.parse())
      case _ =>
    }
  }

  "Test" should "be able to find the json-ld" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = ParserBuilder()
          .id("Schema_NewsArticle")
          .selectHTML()
        val res = RootValue(FetchedValue(parser))
        val parsed = res.parse()
        println(parsed)
      case _ =>
    }
  }

}
