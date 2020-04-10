package parser.value

import org.jsoup.nodes.Element
import parser.{SearchNode, SearchValue}

// Will we need this class?
case class HTMLSearchValue() extends SearchValue {

  override def value(element: Element): Option[String] = {
    val result = element.html()
    if (result != "") Some(result)
    else None
  }
}
