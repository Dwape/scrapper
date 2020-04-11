package search

import org.jsoup.nodes.{Document, Element}
import search.action.{SearchAction, SearchActionChain, SearchAttributeValue, SearchAttributes, SearchClasses, SearchId, SearchParent, SearchTag}

import scala.collection.mutable.ListBuffer

case class SearchNode(id: Option[String] = None,
                      tag: Option[String] = None,
                      classes: Option[Seq[String]] = None,
                      attributes: Option[Seq[String]] = None,
                      parent: Option[SearchNode] = None,
                      attributeValue: Option[(String, String)] = None) { // children: Option[Seq[SearchNode]] = None

  /**
   * Finds the element in the document that corresponds to this SearchNode.
   * @return The element, if it was found.
   */
  def find()(implicit document: Document): Option[Element] = { // This should be able to fail.
    val action = buildActionChain()
    val elements = action.execute()
    if (elements.length == 1) Some(elements.head)
    else None
  }

  def id(id: String): SearchNode = {
    SearchNode(Some(id), this.tag, this.classes, this.attributes, this.parent, this.attributeValue)
  }

  def tag(tag: String): SearchNode = {
    SearchNode(this.id, Some(tag), this.classes, this.attributes, this.parent, this.attributeValue)
  }

  def classes(classes: Seq[String]): SearchNode = {
    SearchNode(this.id, this.tag, Some(classes), this.attributes, this.parent, this.attributeValue)
  }

  def attributes(attributes: Seq[String]): SearchNode = {
    SearchNode(this.id, this.tag, this.classes, Some(attributes), this.parent, this.attributeValue)
  }

  def parent(parent: SearchNode): SearchNode = {
    SearchNode(this.id, this.tag, this.classes, this.attributes, Some(parent), this.attributeValue)
  }

  def attributeValue(attribute: String, value: String): SearchNode = {
    SearchNode(this.id, this.tag, this.classes, this.attributes, this.parent, Some(attribute, value))
  }

  protected def buildActionChain(): SearchActionChain = {
    val chain: ListBuffer[SearchAction] = ListBuffer()
    if (id.isDefined) chain += SearchId(id.get)
    if (tag.isDefined) chain += SearchTag(tag.get)
    if (classes.isDefined) chain += SearchClasses(classes.get)
    if (attributes.isDefined) chain += SearchAttributes(attributes.get)
    if (parent.isDefined) chain += SearchParent(parent.get)
    if (attributeValue.isDefined) chain += SearchAttributeValue(attributeValue.get._1, attributeValue.get._2)
    new SearchActionChain(chain.toList)
  }
}

