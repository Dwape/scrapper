package structure

import org.jsoup.nodes.Document
import parser.Parser

// Might be a good idea to rename it
case class FetchedValue(parser: Parser)(implicit document: Document) extends Value {

  override def parse(): String = {
    parser.value() match {
      case Some(result) => s""""$result""""
      case None => "" // We could do something else here
    }
  }
}
