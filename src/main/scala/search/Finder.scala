package search

import org.jsoup.nodes.Document

/**
 * Parses a single value.
 */
trait Finder {

  /**
   * Returns the value of the matching SearchNode.
   * The value will depend on the SearchValue chosen.
   * @param document The document which contains the element.
   * @return An Option which will contain the value if any value is found.
   */
  def value()(implicit document: Document): Option[String]
}
