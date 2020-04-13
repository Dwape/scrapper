package structure

import org.jsoup.nodes.Document

/**
 * Represents an object that can be serialized.
 */
trait Serializable {
  /**
   * Serializes a class into a string in json format.
   * @param document The document from which the value will be taken.
   * @return The parsed value
   */
  def parse()(implicit document: Document): String // to string?
  /**
   * Serializes an object, adding spaces depending on the level of the object inside the nested structure.
   * @param depth The depth of the object.
   * @param document The document from which the value will be taken.
   * @return The parsed value
   */
  def depthParse(depth: Int = 0)(implicit document: Document): String
}
