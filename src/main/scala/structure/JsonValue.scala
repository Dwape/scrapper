package structure

import play.api.libs.json.JsObject
import search.JsonSearchProperty

case class JsonValue(finder: JsonSearchProperty)(implicit json: JsObject) extends LeafValue {
  override def parse(): String = {
    finder.value() match {
      case Some(result) => s""""$result""""
      case None => "" // We could do something else here
    }
  }
  override def depthParse(depth: Int): String = parse()
}
