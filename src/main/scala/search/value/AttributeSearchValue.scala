package search.value

import org.jsoup.nodes.Element
import search.SearchValue

case class AttributeSearchValue(attribute: String) extends SearchValue {

  override def value(element: Element): Option[String] = {
    val result = element.attr(attribute)
    if (result != "") Some(result)
    else None
  }
}
