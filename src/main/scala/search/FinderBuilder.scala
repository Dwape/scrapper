package search

import org.jsoup.nodes.Document
import search.value.{AttributeSearchValue, HTMLSearchValue, TextSearchValue}

/**
 * Builds a Finder that can be used to filter the elements of the DOM and return a specific value from the result.
 *
 * It is important to note that adding two filters of the same time is not possible.
 * It any of the methods is called twice, the first filter will be overwritten.
 */
case class FinderBuilder(node: SearchNode = SearchNode(), index: Int = -1) { // Parser for a single value.

  def id(id: String): FinderBuilder = FinderBuilder(node.id(id), index)

  def tag(tag: String): FinderBuilder = FinderBuilder(node.tag(tag), index)

  def classes(classes: Seq[String]): FinderBuilder = FinderBuilder(node.classes(classes), index)

  def attributes(attributes: Seq[String]): FinderBuilder = FinderBuilder(node.attributes(attributes), index)

  def parent(parent: SearchNode): FinderBuilder = FinderBuilder(node.parent(parent), index)

  def attributeValue(attribute: String, value: String): FinderBuilder = FinderBuilder(node.attributeValue(attribute, value), index)

  def forIndex(index: Int): FinderBuilder = FinderBuilder(node, index)

  def selectAttribute(attribute: String) = new ValueFinder(node, AttributeSearchValue(attribute), index)

  def selectHTML() = new ValueFinder(node, HTMLSearchValue(), index)

  def selectText() = new ValueFinder(node, TextSearchValue(), index)
}


