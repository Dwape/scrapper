package structure

import org.jsoup.nodes.Document

/**
 * Represents a Json boolean value
 */
case class BooleanValue(value: Boolean) extends LeafValue {
  override def parse()(implicit document: Document): String = s"""$value"""

  override def depthParse(depth: Int)(implicit document: Document): String = parse()
}
