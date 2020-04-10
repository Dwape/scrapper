package parser

import org.jsoup.nodes.Document

class SetValueParser(result: String) extends Parser {

  def value()(implicit document: Document): Option[String] = Some(result)
}
