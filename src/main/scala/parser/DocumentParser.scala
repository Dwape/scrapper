package parser
import org.jsoup.nodes.Document
import play.api.libs.json.{JsObject, Json}
import search.{FinderBuilder, JsonSearchPropertyBuilder}
import structure.{JsonObjectValue, JsonValue, ObjectValue, Property, Value}

class DocumentParser extends Parser {

  lazy val structure: Value = createStructure()

  override def parse(document: Document): String = {
    implicit val doc: Document = document // How should this be done?
    this.structure.depthParse()
  }

  protected def createStructure(): Value = {
    val parser = FinderBuilder()
      .id("Schema_NewsArticle")
      .selectHTML()
    val structure = JsonObjectValue(parser)
    // We are parsing the json many times.
    val articleId = JsonSearchPropertyBuilder().find("url", parser)
    val imageId = JsonSearchPropertyBuilder().field("image").find("url", parser)
    val publisherId = JsonSearchPropertyBuilder().field("publisher").find("url", parser)
    val logoId = JsonSearchPropertyBuilder().field("publisher").field("logo").find("url", parser)
    // Should we add the id?
    structure
      .addPropertyAtIndex(Property("@id", JsonValue(articleId)), 0)
      .addPropertyAtIndex(Property("@id", JsonValue(imageId)), 0, List("image"))
      .addPropertyAtIndex(Property("@id", JsonValue(publisherId)), 0, List("publisher"))
      .addPropertyAtIndex(Property("@id", JsonValue(logoId)), 0, List("publisher", "logo"))
  }
}
