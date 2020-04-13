package structure

import org.jsoup.nodes.Document

/**
 * Represents a property in a Json Object, containing a key and a value.
 * @param key The property's key.
 * @param value The property's value.
 */
case class Property(key: String, value: Value) extends Serializable {
  override def parse()(implicit document: Document): String = s""""$key": ${value.parse()}"""
  override def depthParse(depth: Int = 0)(implicit document: Document): String = {
    s"""${"  "*depth}"$key": ${value.depthParse(depth)}"""
  }
}
