package structure

import org.jsoup.nodes.Document

case class BooleanValue(value: Boolean) extends LeafValue {
  override def parse()(implicit document: Document): String = s"""$value"""

  override def depthParse(depth: Int)(implicit document: Document): String = parse()
}
