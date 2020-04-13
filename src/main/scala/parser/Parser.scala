package parser

import org.jsoup.nodes.Document
import structure.Value

/**
 * Parses a Document, returning the json-ld describing the Things in it.
 */
abstract class Parser {

  lazy val structure: Value = createStructure()

  /**
   * Takes a DOM and returns the corresponding Json-ld.
   * @param document
   * @return The json-ld or an empty string if the DOM cannot be parsed.
   */
  def parse(document: Document): String = {
    implicit val doc: Document = document // How should this be done?
    this.structure.depthParse()
  }

  protected def createStructure(): Value
}
