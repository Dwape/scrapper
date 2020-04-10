package parser

import org.jsoup.nodes.Document

class ValueParser (node: SearchNode, value: SearchValue) extends Parser {

  def value()(implicit document: Document): Option[String] = {
    node.find().flatMap(e => value.value(e))
  }
}
