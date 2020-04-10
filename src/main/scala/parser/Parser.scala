package parser

import org.jsoup.nodes.Document

trait Parser {

  /**
   * Takes a DOM and returns the corresponding Json-ld.
   * @param document
   * @return
   */
  def parse(document: Document): String // What should we return here?
  // Should we validate before of after?
}
