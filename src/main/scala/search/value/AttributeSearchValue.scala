package search.value

import org.jsoup.nodes.Element
import search.SearchValue

/**
 * Returns the value of the specified attribute for the provided element.
 * @param attribute The attribute whose value will be returned.
 */
case class AttributeSearchValue(attribute: String) extends SearchValue {

  /**
   * Finds a value.
   * @param element The element whose value will be returned.
   * @return The value, if there is any.
   */
  override def value(element: Element): Option[String] = {
    val result = element.attr(attribute)
    if (result != "") Some(result)
    else None
  }
}
