package search

import org.jsoup.nodes.Document
import play.api.libs.json.{JsObject, Json}

case class JsonSearchProperty(keys: Seq[String], finder: Finder) {

  def value()(implicit document: Document): Option[String] = {
    // Check what to do if it fails.
    val json = Json.toJson(finder.value()).as[JsObject]
    findValue(json)
  }

  def value(json: JsObject): Option[String] = {
    findValue(json)
  }

  protected def findValue(json: JsObject): Option[String] = {
    val result = keys.init.foldLeft(json)((data, key) => data.\(key).get.as[JsObject])
    Some(result.\(keys.last).get.toString().replaceAll(""""""", ""))
  }
}
