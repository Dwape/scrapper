package registry
import parser.Parser

class UrlRegistry extends Registry {

  override def findParser(url: String): Option[Parser] = {
    // Implement method.
    None
  }
}
