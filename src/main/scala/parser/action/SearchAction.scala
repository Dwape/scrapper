package parser.action

import org.jsoup.nodes.{Document, Element}

trait SearchAction {

  def find()(implicit document: Document): Seq[Element]

  def filter(elements: Seq[Element])(implicit document: Document): Seq[Element]
}
