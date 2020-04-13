package search.value

import org.jsoup.nodes.Element
import search.{SearchNode, SearchValue}

/**
 * Returns the HTML for the provided element.
 */
case class HTMLSearchValue() extends SearchValue {

  /**
   * Finds a value.
   * @param element The element whose value will be returned.
   * @return The value, if there is any.
   */
  override def value(element: Element): Option[String] = {
    val result = element.html()
    if (result != "") Some(result)
    else None
  }
}
