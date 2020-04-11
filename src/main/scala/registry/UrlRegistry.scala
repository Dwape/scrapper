package registry
import parser.{DocumentParser, Parser, SecondParser}

class UrlRegistry extends Registry {

  override def findParser(url: String): Option[Parser] = url match {
    // Implement method.
    case link if link.startsWith("http://www.lanacion.com.ar/") => Some(new DocumentParser())
    case link if link.startsWith("http://www.imdb.com/title/") => Some(new SecondParser())
    case _ => None
  }
}
