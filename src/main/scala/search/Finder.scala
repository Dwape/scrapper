package search

import org.jsoup.nodes.Document

/**
 * Parses a single value.
 */
trait Finder {

  def value()(implicit document: Document): Option[String]
}
