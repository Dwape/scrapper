package parser
import org.jsoup.nodes.Document
import play.api.libs.json.{JsObject, Json}
import search.JsonSearchPropertyBuilder
import structure.{DataStructureBuilder, JsonValue, StringValue}

class DocumentParser extends Parser {

  override def parse(document: Document): String = {
    val string = """{"name": {"test": "hello"}}"""
    println(string)
    implicit val json: JsObject = Json.parse(string).as[JsObject]
    val finder = JsonSearchPropertyBuilder().field("name").find("test")
    val structure = DataStructureBuilder(StringValue("id"), StringValue("type"))
      .addProperty("name", JsonValue(finder))
        .build()
    println(structure.depthParse())
    "TODO"
  }
}
