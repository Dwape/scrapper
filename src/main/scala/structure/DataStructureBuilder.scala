package structure

import play.api.libs.json.{JsArray, JsObject, JsString, JsValue, Json}

/**
 * Creates a new ObjectValue that represents a Thing as defined in http://schema.org
 * Therefore, only the basic properties are required but more properties can be added.
 * @param id The id of the Thing.
 * @param `type` The type of the Thing.
 * @param properties Initial properties of the Thing.
 */
case class DataStructureBuilder(id: LeafValue, `type`: LeafValue, properties: List[Property] = List()) {

  def withContext(value: LeafValue): DataStructureBuilder = {
    DataStructureBuilder(id, `type`, properties :+ Property("@context", value))
  }

  def addProperty(name: String, value: Value): DataStructureBuilder = {
    val property: Property = Property(name, value)
    DataStructureBuilder(id, `type`, properties :+ property)
  }

  def build(): ObjectValue = {
    // val result = properties :+ Property("@id", id) :+ Property("@type", `type`)
    val result = Property("@id", id) :: Property("@type", `type`) :: properties
    ObjectValue(result)
  }
}
