package registry

import parser.Parser

/**
 * Allows the retrieval of parsers for different url prefixes.
 */
trait Registry {

  /**
   * Returns the parser that corresponds to a specific url.
   * @param url The url that will be parsed or its prefix.
   * @return An Option[Parser] containing the Parser only if one was found for the provided id.
   */
  def findParser(url: String): Option[Parser]
}
