package structure

import org.jsoup.nodes.Document
import play.api.libs.json.JsObject
import search.JsonSearchProperty

case class JsonValue(finder: JsonSearchProperty) extends LeafValue {
  override def parse()(implicit document: Document): String = {
    finder.value() match {
      case Some(result) => s""""$result""""
      case None => "" // We could do something else here
    }
  }
  override def depthParse(depth: Int)(implicit document: Document): String = parse()

  def parse(json: JsObject): String = {
    finder.value(json) match {
      case Some(result) => result
      case _ => "" // Check what to do here
    }
  }
}
