package search.action

import org.jsoup.nodes.{Document, Element}

/**
 * An action performed on a DOM to find elements.
 */
trait SearchAction {

  /**
   * Finds all the elements in the document that match the search criteria.
   * Called when the SearchAction is the first one in a chain.
   * @param document The document which will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  def find()(implicit document: Document): Seq[Element]

  /**
   * Filters a Sequence of elements, returning only those which match the search criteria.
   * This should only be called after a find on another SearchAction which returns the input for this method.
   * @param elements The elements to be filtered.
   * @param document The document that will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  def filter(elements: Seq[Element])(implicit document: Document): Seq[Element]
  // The document shouldn't really be required here as we already have all the elements.
}
