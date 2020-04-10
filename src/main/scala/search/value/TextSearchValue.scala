package search.value

import org.jsoup.nodes.Element
import search.SearchValue

case class TextSearchValue() extends SearchValue {

  override def value(element: Element): Option[String] = {
    val result = element.text()
    if (result != "") Some(result)
    else None
  }
}
