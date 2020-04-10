package search

import org.jsoup.nodes.Document
import search.value.{AttributeSearchValue, HTMLSearchValue, TextSearchValue}

case class FinderBuilder(node: SearchNode = SearchNode()) { // Parser for a single value.

  def id(id: String): FinderBuilder = FinderBuilder(node.id(id))

  def tag(tag: String): FinderBuilder = FinderBuilder(node.tag(tag))

  def classes(classes: Seq[String]): FinderBuilder = FinderBuilder(node.classes(classes))

  def attributes(attributes: Seq[String]): FinderBuilder = FinderBuilder(node.attributes(attributes))

  def parent(parent: SearchNode): FinderBuilder = FinderBuilder(node.parent(parent))

  def selectAttribute(attribute: String) = new ValueFinder(node, AttributeSearchValue(attribute))

  def selectHTML() = new ValueFinder(node, HTMLSearchValue())

  def selectText() = new ValueFinder(node, TextSearchValue())
}


