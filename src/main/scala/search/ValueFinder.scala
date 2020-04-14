package search

import org.jsoup.nodes.Document

/**
 * An implementation of the Finder trait.
 * @param node The SearchNode that represents an element in the DOM.
 * @param value The SearchValue that defines which value from the element to return.
 */
class ValueFinder(node: SearchNode, value: SearchValue, index: Int = -1) extends Finder {

  /**
   * Returns the value of the matching SearchNode.
   * The value will depend on the SearchValue chosen.
   * @param document The document which contains the element.
   * @return An Option which will contain the value if any value is found.
   */
  def value()(implicit document: Document): Option[String] = {
    if (index == -1) node.find().flatMap(e => value.value(e))
    else node.find(index).flatMap(e => value.value(e))
  }
}
