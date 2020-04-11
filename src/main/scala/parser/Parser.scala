package parser

import org.jsoup.nodes.Document

trait Parser {

  /**
   * Takes a DOM and returns the corresponding Json-ld.
   * @param document
   * @return
   */
  // Should we receive a url here? Maybe we could receive some params.
  def parse(document: Document): String // What should we return here?
  // Should we validate before of after?
}
