package search.action
import org.jsoup.nodes.{Document, Element}
import scala.jdk.CollectionConverters._

case class SearchAttributeValue(attribute: String, value: String) extends SearchAction {

  override def find()(implicit document: Document): Seq[Element] = {
    document
      .getElementsByAttribute(attribute)
      .asScala
      .toSeq
      .filter {
        element => element.hasAttr(attribute) && element.attr(attribute) == value
      }
  }

  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter {
      element => element.hasAttr(attribute) && element.attr(attribute) == value
    }
  }
}
