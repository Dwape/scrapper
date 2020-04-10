package parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParserTest extends AnyFlatSpec with Matchers {

  "SearchNode" should "find an existing element" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"></div></body></html>")
    val searchNode = SearchNode().id("test")
    val element = searchNode.find()
    element shouldBe a[Some[_]]
    element.get.id should be("test")
  }

  "SearchNode" should "not find a non existing element" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body></body></html>")
    val searchNode = SearchNode().id("test")
    val element = searchNode.find()
    element shouldBe None
  }

  "SearchNode" should "return None when more than one elements are found" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div class=\"test\"></div><div class=\"test\"></div></body></html>")
    val searchNode = SearchNode().classes(List("test"))
    val element = searchNode.find()
    element shouldBe None
  }

  "SearchNode" should "return None when no filters are applied" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body></body></html>")
    val searchNode = SearchNode()
    val element = searchNode.find()
    element shouldBe None
  }

  "SearchNode" should "be able to find by tag" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div></div></body></html>")
    val searchNode = SearchNode().tag("div")
    val element = searchNode.find()
    element shouldBe a[Some[_]]
    element.get.tagName() should be("div")
  }

  "SearchNode" should "be able to filter by tag" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"></div></body></html>")
    val searchNode = SearchNode().id("test").tag("a")
    val element = searchNode.find()
    element shouldBe None
  }

  "SearchNode" should "be able to find by classes" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div class=\"test\"></div></body></html>")
    val searchNode = SearchNode().classes(List("test"))
    val element = searchNode.find()
    element shouldBe a[Some[_]]
    element.get.classNames().contains("test") should be(true)
  }

  "SearchNode" should "be able to filter by classes" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"></div></body></html>")
    val searchNode = SearchNode().id("test").classes(List("test"))
    val element = searchNode.find()
    element shouldBe None
  }

  "SearchNode" should "be able to find by attributes" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a class=\"test\" href=\"\"></a></body></html>")
    val searchNode = SearchNode().attributes(List("href"))
    val element = searchNode.find()
    element shouldBe a[Some[_]]
    element.get.classNames().contains("test") should be(true)
  }

  "SearchNode" should "be able to filter by attributes" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><a id=\"test\" class=\"style\"></a></body></html>")
    val searchNode = SearchNode().id("test").attributes(List("href"))
    val element = searchNode.find()
    element shouldBe None
  }

  "SearchNode" should "be able to find by parent" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"><a class=\"style\"></a></div></body></html>")
    val searchNode = SearchNode().parent(SearchNode().id("test"))
    val element = searchNode.find()
    element shouldBe a[Some[_]]
    element.get.classNames().contains("style") should be(true)
  }

  "SearchNode" should "be able to filter by parent" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"duck\"><a id=\"test\"></a></div></body></html>")
    val searchNode = SearchNode().id("test").parent(SearchNode().id("title"))
    val element = searchNode.find()
    element shouldBe None
  }

  "Parser" should "be able to get text values" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"><p class=\"text\">Example</p></div></body></html>")

    val result = ParserBuilder()
      .classes(List("text"))
      .parent(SearchNode().id("test"))
      .selectText()
      .value()
    result shouldBe a[Some[_]]
    result.get should be("Example")
  }

  "Parser" should "be able to get html values" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"><p class=\"text\">Example</p></div></body></html>")

    val result = ParserBuilder()
      .id("test")
      .selectHTML()
      .value()
    result shouldBe a[Some[_]]
    result.get should be("<p class=\"text\">Example</p>")
  }

  "Parser" should "be able to get attribute values" in {
    implicit val document: Document = Jsoup.parse("<html><head></head><body><div id=\"test\"><a href=\"google.com\"></a></div></body></html>")

    val result = ParserBuilder()
      .parent(SearchNode().id("test"))
      .selectAttribute("href")
      .value()
    result shouldBe a[Some[_]]
    result.get should be("google.com")
  }
}
