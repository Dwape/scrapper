package parser.action
import org.jsoup.nodes.{Document, Element}
import scala.jdk.CollectionConverters._

case class SearchAttributes(attributes: Seq[String]) extends SearchAction {

  override def find()(implicit document: Document): Seq[Element] = {
    document
      .getElementsByAttribute(attributes.head)
      .asScala
      .toSeq
      .filter{
        element => attributes.tail.map {
          attribute => element.hasAttr(attribute)
        }.forall(b => b)
      }
  }

  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter{
      element => attributes.map {
        attribute => element.hasAttr(attribute)
      }.forall(b => b)
    }
  }
}
