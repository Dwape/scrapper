package search

import play.api.libs.json.JsObject

case class JsonSearchProperty(keys: Seq[String]) {

  def value()(implicit json: JsObject): Option[String] = {
    // Check what to do if it fails.
    val result = keys.init.foldLeft(json)((data, key) => data.\(key).get.as[JsObject])
    Some(result.\(keys.last).get.toString().replaceAll(""""""", ""))
  }
}
