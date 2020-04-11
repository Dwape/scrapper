package structure

import org.jsoup.nodes.Document

case class Property(key: String, value: Value) extends Serializable {
  override def parse()(implicit document: Document): String = s""""$key": ${value.parse()}"""
  override def depthParse(depth: Int = 0)(implicit document: Document): String = {
    s"""${"  "*depth}"$key": ${value.depthParse(depth)}"""
  }
}
