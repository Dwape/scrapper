package structure

import org.jsoup.nodes.Document

/**
 * Represents a Json number value
 */
case class NumberValue(value: Double) extends LeafValue { // Should this be a double?
  override def parse()(implicit document: Document): String = s"""$value"""

  override def depthParse(depth: Int = 0)(implicit document: Document): String = parse()
}
