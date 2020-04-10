package parser

import org.jsoup.nodes.Document

trait Parser {

  def value()(implicit document: Document): Option[String]
}
