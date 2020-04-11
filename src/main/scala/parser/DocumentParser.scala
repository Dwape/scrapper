package parser
import org.jsoup.nodes.Document
import play.api.libs.json.{JsObject, Json}
import search.{FinderBuilder, JsonSearchPropertyBuilder}
import structure.{JsonValue, ObjectValue, Property}

class DocumentParser extends Parser {

  override def parse(document: Document): String = {
    implicit val doc: Document = document // How should this be done?
    val parser = FinderBuilder()
      .id("Schema_NewsArticle")
      .selectHTML()
    val json = parser.value().get
    val structure = ObjectValue().fromJson(json)
    implicit val jsonld = Json.parse(json).as[JsObject]
    val articleId = JsonSearchPropertyBuilder().find("url")
    val imageId = JsonSearchPropertyBuilder().field("image").find("url")
    val publisherId = JsonSearchPropertyBuilder().field("publisher").find("url")
    val logoId = JsonSearchPropertyBuilder().field("publisher").field("logo").find("url")
    // Should we add the id?
    structure
      .addPropertyAtIndex(Property("@id", JsonValue(articleId)), 0)
      .addPropertyAtIndex(Property("@id", JsonValue(imageId)), 0, List("image"))
      .addPropertyAtIndex(Property("@id", JsonValue(publisherId)), 0, List("publisher"))
      .addPropertyAtIndex(Property("@id", JsonValue(logoId)), 0, List("publisher", "logo"))
      .depthParse()
    // We need to add some attributes.
    // structure.depthParse()
  }
}
