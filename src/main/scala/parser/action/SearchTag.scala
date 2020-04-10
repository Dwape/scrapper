package parser.action
import org.jsoup.nodes.{Document, Element}
import scala.jdk.CollectionConverters._

case class SearchTag(tag: String) extends SearchAction {

  override def find()(implicit document: Document): Seq[Element] = {
    document.getElementsByTag(tag).asScala.toSeq
  }

  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter(element => element.tagName() == tag)
  }
}
