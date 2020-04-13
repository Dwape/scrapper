package structure

import org.jsoup.nodes.Document

/**
 * Represents a Json string value
 */
case class StringValue(value: String) extends LeafValue {
  override def parse()(implicit document: Document): String = s""""$value""""

  override def depthParse(depth: Int = 0)(implicit document: Document): String = parse()
}
