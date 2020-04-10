package structure

import org.jsoup.nodes.Document
import search.Finder

// Might be a good idea to rename it
case class FetchedValue(parser: Finder)(implicit document: Document) extends LeafValue {

  // We could have a version that computes the value when it is created.

  // Should we allow the value that it is parsed to to not be as string?
  override def parse(): String = {
    parser.value() match {
      case Some(result) => s""""$result""""
      case None => "" // We could do something else here
    }
  }
  override def depthParse(depth: Int = 0): String = parse()
}
