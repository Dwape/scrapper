package search.action

import org.jsoup.nodes.{Document, Element}
import search.SearchNode
import scala.jdk.CollectionConverters._

/**
 * Finds all elements with the provided parent.
 * The parent is a SearchNode, which means that queries can also be executed on the parent itself.
 * @param parent The parent that the element must contain.
 */
case class SearchParent(parent: SearchNode) extends SearchAction {

  /**
   * Finds all the elements in the document that match the search criteria.
   * Called when the SearchAction is the first one in a chain.
   * @param document The document which will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
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

  /**
   * Filters a Sequence of elements, returning only those which match the search criteria.
   * This should only be called after a find on another SearchAction which returns the input for this method.
   * @param elements The elements to be filtered.
   * @param document The document that will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter { element =>
      parent.find() match {
        case Some(p) => p.equals(element.parent())
        case _ => false
      }
    }
  }
}
