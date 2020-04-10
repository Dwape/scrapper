package parser.action

import org.jsoup.nodes.{Document, Element}
import parser.SearchNode
import scala.jdk.CollectionConverters._

case class SearchParent(parent: SearchNode) extends SearchAction {

  override def find()(implicit document: Document): Seq[Element] = {
    document
      .getAllElements
      .asScala
      .toSeq
      .filter { element =>
        parent.find() match {
          case Some(p) => p.equals(element.parent())
          case _ => false
        }
      }
  }

  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter { element =>
      parent.find() match {
        case Some(p) => p.equals(element.parent())
        case _ => false
      }
    }
  }
}
