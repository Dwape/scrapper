package parser
import org.jsoup.nodes.Document

class SecondParser extends Parser {
  /**
   * Takes a DOM and returns the corresponding Json-ld.
   *
   * @param document
   * @return
   */
  override def parse(document: Document): String = {
    "Todo"
  }
}
