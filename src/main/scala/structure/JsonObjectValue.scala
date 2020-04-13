package structure

import org.jsoup.nodes.Document
import play.api.libs.json.{JsObject, Json}
import search.{Finder, JsonSearchProperty}

/**
 * An object value built using a Json.
 *
 * The Json is converted and all operations are executed when the object is parsed.
 * This allows the Document to be changed while still using the same structure to get the json-ld.
 *
 * @param json The json from which the object's properties will be taken.
 * @param operations The operations executed on the Object.
 */
case class JsonObjectValue(json: Finder, operations: List[(Property, List[String], Int)] = List()) extends Value { // This could be its own type

  override def parse()(implicit document: Document): String = {
    val result = convertToObject()
    result.parse()
  }

  override def depthParse(depth: Int)(implicit document: Document): String = {
    val result = convertToObject()
    result.depthParse()
  }

  def addProperty(property: Property, keys: List[String] = List()): JsonObjectValue = {
    JsonObjectValue(json, operations :+ (property, keys, -1))
  }

  def addPropertyAtIndex(property: Property, index: Int, keys: List[String] = List()): JsonObjectValue = {
    JsonObjectValue(json, operations :+ (property, keys, index))
  }

  protected def convertToObject()(implicit document: Document): ObjectValue = {
    json.value() match {
      case Some(string) =>
        val parsed = Json.parse(string).as[JsObject]
        // val parsed = Json.toJson(string).as[JsObject]
        val result = ObjectValue().fromJson(string)
        operations.foldLeft(result)((last, current) => {
          // Check if the property is a json value.
          val property = computeJsonProperty(current._1, parsed)
          // val property = current._1
          if (current._3 == -1) {
            last.addProperty(property, current._2)
          } else {
            last.addPropertyAtIndex(property, current._3, current._2)
          }})
      case None => DataStructureBuilder(StringValue("id"), StringValue("type")).build() // What should we do here?
    }
  }

  protected def computeJsonProperty(property: Property, json: JsObject): Property = property match {
    case Property(key, value: JsonValue) => Property(key, StringValue(value.parse(json)))
    case _ => property
  }
}
