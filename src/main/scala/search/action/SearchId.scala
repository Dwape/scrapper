package search.action
import org.jsoup.nodes.{Document, Element}

/**
 * Finds all the elements with the provided id.
 *
 * It is important to note this action returns a Seq[Element]
 * Even though the id should be unique,
 * it could be repeated and several elements would be returned.
 * The result is also a Seq[Elements] to allow easier chaining between different SearchActions.
 *
 * @param id The id that the element must contain.
 */
case class SearchId(id: String) extends SearchAction {

  /**
   * Finds all the elements in the document that match the search criteria.
   * Called when the SearchAction is the first one in a chain.
   * @param document The document which will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  override def find()(implicit document: Document): Seq[Element] = {
    val result = document.getElementById(id)
    if (result != null) Seq(result)
    else Seq()
  }

  /**
   * Filters a Sequence of elements, returning only those which match the search criteria.
   * This should only be called after a find on another SearchAction which returns the input for this method.
   * @param elements The elements to be filtered.
   * @param document The document that will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter(element => element.id() == id)
    // This method should never be called.
  }
}
