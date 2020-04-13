package search

import org.jsoup.nodes.Document
import play.api.libs.json.{JsObject, Json}

/**
 * Finds a property inside a Json.
 *
 * The finder defines which element in the DOM contains the Json and where specifically inside the element.
 * The finder must return the Json when the value() method is called.
 *
 * The keys define the name of the property inside the Json.
 * This property can be nested inside several objects and that is the reason why many keys are accepted.
 *
 * If no keys are provided, the whole Json will be returned.
 *
 * @param keys The keys that form the property.
 * @param finder The finder used to find the Json in the document.
 */
case class JsonSearchProperty(keys: Seq[String], finder: Finder) {

  /**
   * Returns the value for a specific property.
   * @param document The document where the Json will be searched.
   * @return The value of the property, if found.
   */
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
