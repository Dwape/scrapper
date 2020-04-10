package search.action

import org.jsoup.nodes.{Document, Element}

import scala.jdk.CollectionConverters._

case class SearchClasses(classes: Seq[String]) extends SearchAction {

  override def find()(implicit document: Document): Seq[Element] = {
    document
      .getElementsByClass(classes.head)
      .asScala
      .toSeq
      .filter{
        element => classes.tail.map {
          `class` => element.hasClass(`class`)
        }.forall(b => b)
      }
  }

  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter{
      element => classes.map {
        `class` => element.hasClass(`class`)
      }.forall(b => b)
    }
  }
}
