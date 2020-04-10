package search

import org.jsoup.nodes.Document

class ValueFinder(node: SearchNode, value: SearchValue) extends Finder {

  def value()(implicit document: Document): Option[String] = {
    node.find().flatMap(e => value.value(e))
  }
}
