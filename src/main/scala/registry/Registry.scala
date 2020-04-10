package registry

import parser.Parser

trait Registry {

  def findParser(url: String): Option[Parser]
}
