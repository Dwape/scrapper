package structure

import fetcher.UrlFetcher
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import parser.DocumentParser
import play.api.libs.json.Json
import search.{FinderBuilder, SearchNode}
import response.{Response, SuccessfulResponse}
import scrapper.{Scrapper, UrlScrapper}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

// Change name
class StructureTest extends AnyFlatSpec with Matchers {

  "Values" should "be parsable" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a id=\"link\" href=\"http://schema.org\"></a></body></html>")
    val parser = FinderBuilder().id("link").selectAttribute("href")
    val result = RootValue(ObjectValue(List(Property("name", StringValue("Tom")), Property("value", FetchedValue(parser)))))
    println(result.parse())
  }

  "Test" should "be able to fetch a real site" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = FinderBuilder().classes(List("titulo")).selectText()
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
        val parser = FinderBuilder()
          .id("Schema_NewsArticle")
          .selectHTML()
        val res = RootValue(FetchedValue(parser))
        val parsed = res.parse()
        println(parsed)
      case _ =>
    }
  }

  "Test" should "be able to build a DataStructure" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a id=\"link\" href=\"http://www.lanacion.com.ar\"></a></body></html>")
    val parser = FinderBuilder()
      .id("link")
      .selectAttribute("href")
    val data = DataStructureBuilder(FetchedValue(parser), StringValue("NewsArticle"))
      .withContext(StringValue("http://schema.org"))
        .build()
    println(data.parse())
  }

  "Test" should "convert json-ld to DataStructure" in {
    val response: Response[Document] = Await.result(new UrlFetcher().fetch("http://www.lanacion.com.ar/2351900", 10000), 5 seconds)
    response shouldBe a[SuccessfulResponse[_]]
    response match {
      case SuccessfulResponse(result) =>
        implicit val document: Document = result
        val parser = FinderBuilder()
          .id("Schema_NewsArticle")
          .selectHTML()
        val json = parser.value()
        val res = ObjectValue().fromJson(json.get)
        println(res.depthParse())
        // println(Json.parse(res.parse()))
        // Json.parse(res.parse())
      case _ =>
    }
  }

  "Parser" should "should be able to parse" in {
    val document: Document = Jsoup.parse("<html><head></head><body><a id=\"link\" href=\"http://www.lanacion.com.ar\"></a></body></html>")
    val parser = new DocumentParser()
    parser.parse(document)
  }

  "Scrapper" should "scrap a site" in {
    val scrapper = new UrlScrapper()
    val result = Await.result(scrapper.scrap("http://www.imdb.com/title/tt0111161/"), 5 seconds)
    result match {
      case SuccessfulResponse(json) => println(json)
      case _ => println("Something went wrong")
    }

  }

}
