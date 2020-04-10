package structure

import play.api.libs.json.{JsArray, JsNumber, JsObject, JsString, JsValue, Json}

case class ObjectValue(properties: List[Property] = List()) extends Value {
  override def parse(): String = {
    properties
      .foldLeft("{")((acc, current) => s"$acc${current.parse()},").dropRight(2)+"}" // Do we need to add new lines?
  }
  override def depthParse(depth: Int = 0): String = {
    properties
      .foldLeft(s"{\n")((acc, current) => s"$acc${current.depthParse(depth+1)},\n")
      .dropRight(2)+s"\n${"  "*depth}}"
      // .replaceAll(",", s",\n${"  "*depth}")
  }

  // We could also receive a Json
  def fromJson(json: String): ObjectValue = {
    /*
    val parsed = Json.parse(String).as[JsObject]
    val result = parsed.fields.map {
      case (field, value) => Property(field, fromJson(value))
    }
    ObjectValue(result.toList)
     */
    val parsed = Json.parse(json).as[JsObject]
    fromJson(parsed).asInstanceOf[ObjectValue]
  }

  // This probably won't be used.
  protected def fromJson(json: JsValue): Value = json match {
    // Check if this works
    case value: JsObject => ObjectValue(value.fields.map {
      case (field, value) => Property(field, fromJson(value))}.toList)
    case value: JsArray => ArrayValue(value.value.map { v => fromJson(v) }.toList)
    case value: JsString =>
      StringValue(value.toString().replaceAll(""""""", ""))
    case value: JsNumber => StringValue(value.value.toString())
    // Consider other types.
  }
}
