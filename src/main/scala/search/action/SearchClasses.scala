package search.action

import org.jsoup.nodes.{Document, Element}

import scala.jdk.CollectionConverters._

/**
 * Finds all the elements that contain all the classes provided.
 * @param classes The classes that the elements must contain.
 */
case class SearchClasses(classes: Seq[String]) extends SearchAction {

  /**
   * Finds all the elements in the document that match the search criteria.
   * Called when the SearchAction is the first one in a chain.
   * @param document The document which will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
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

  /**
   * Filters a Sequence of elements, returning only those which match the search criteria.
   * This should only be called after a find on another SearchAction which returns the input for this method.
   * @param elements The elements to be filtered.
   * @param document The document that will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter{
      element => classes.map {
        `class` => element.hasClass(`class`)
      }.forall(b => b)
    }
  }
}
