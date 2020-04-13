package search.value

import org.jsoup.nodes.Element
import search.SearchValue

/**
 * Returns the text for the provided element.
 */
case class TextSearchValue() extends SearchValue {

  /**
   * Finds a value.
   * @param element The element whose value will be returned.
   * @return The value, if there is any.
   */
  override def value(element: Element): Option[String] = {
    val result = element.text()
    if (result != "") Some(result)
    else None
  }
}
