package search

import org.jsoup.nodes.Element

/**
 * Finds the value of an element.
 */
trait SearchValue {

  /**
   * Finds a value.
   * @param element The element whose value will be returned.
   * @return The value, if there is any.
   */
  def value(element: Element): Option[String]
}
