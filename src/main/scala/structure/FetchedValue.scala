package structure

import org.jsoup.nodes.Document
import search.Finder

/**
 * A value that is retrieved from an element in the DOM.
 * @param parser The Finder that will be used to retrieve the value.
 */
case class FetchedValue(parser: Finder) extends LeafValue {

  // We could have a version that computes the value when it is created.

  // Should we allow the value that it is parsed to to not be as string?
  override def parse()(implicit document: Document): String = {
    parser.value() match {
      case Some(result) => s""""$result""""
      case None => "" // We could do something else here
    }
  }
  override def depthParse(depth: Int = 0)(implicit document: Document): String = parse()
}
