package parser
import org.jsoup.nodes.Document
import search.FinderBuilder
import structure.{JsonObjectValue, ObjectValue, Value}

class SecondParser extends Parser {

  lazy val structure: Value = createStructure()
  /**
   * Takes a DOM and returns the corresponding Json-ld.
   *
   * @param document
   * @return
   */
  override def parse(document: Document): String = {
    implicit val doc: Document = document // How should this be done?
    this.structure.depthParse()
  }

  protected def createStructure(): Value = {
    val parser = FinderBuilder()
      .attributeValue("type", "application/ld+json")
      .selectHTML()
    JsonObjectValue(parser)
  }
}
