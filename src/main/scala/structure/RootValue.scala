package structure

import org.jsoup.nodes.Document

// This will probably not be needed.
case class RootValue(value: Value) extends Value {
  // Doesn't add anything.
  override def parse()(implicit document: Document): String = value.parse()
  override def depthParse(depth: Int = 0)(implicit document: Document): String = parse()
}
