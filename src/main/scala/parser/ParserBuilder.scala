package parser

import org.jsoup.nodes.Document
import parser.value.{AttributeSearchValue, HTMLSearchValue, TextSearchValue}

case class ParserBuilder(node: SearchNode = SearchNode()) { // Parser for a single value.

  def id(id: String): ParserBuilder = ParserBuilder(node.id(id))

  def tag(tag: String): ParserBuilder = ParserBuilder(node.tag(tag))

  def classes(classes: Seq[String]): ParserBuilder = ParserBuilder(node.classes(classes))

  def attributes(attributes: Seq[String]): ParserBuilder = ParserBuilder(node.attributes(attributes))

  def parent(parent: SearchNode): ParserBuilder = ParserBuilder(node.parent(parent))

  def selectAttribute(attribute: String) = new ValueParser(node, AttributeSearchValue(attribute))

  def selectHTML() = new ValueParser(node, HTMLSearchValue())

  def selectText() = new ValueParser(node, TextSearchValue())

  def setValue(value: String) = new SetValueParser(value) // This could be improved
}


