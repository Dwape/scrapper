package structure

import play.api.libs.json.{JsArray, JsObject, JsString, JsValue, Json}

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
