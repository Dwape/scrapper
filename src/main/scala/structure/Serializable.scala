package structure

import org.jsoup.nodes.Document

// This might be temporary. It is probably a good idea to just use some library for this.
trait Serializable {
  def parse()(implicit document: Document): String // to string?
  def depthParse(depth: Int = 0)(implicit document: Document): String
}
