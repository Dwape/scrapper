package structure

import org.jsoup.nodes.Document
import play.api.libs.json.{JsArray, JsBoolean, JsNumber, JsObject, JsString, JsValue, Json}

/**
 * Represents a Json object, containing properties.
 * These properties can be Objects, Arrays or other values, so this structure is nested.
 * @param properties The properties of the object.
 */
case class ObjectValue(properties: List[Property] = List()) extends Value {
  override def parse()(implicit document: Document): String = {
    properties
      .foldLeft("{")((acc, current) => s"$acc${current.parse()},").dropRight(2)+"}" // Do we need to add new lines?
  }
  override def depthParse(depth: Int = 0)(implicit document: Document): String = {
    properties
      .foldLeft(s"{\n")((acc, current) => s"$acc${current.depthParse(depth+1)},\n")
      .dropRight(2)+s"\n${"  "*depth}}"
      // .replaceAll(",", s",\n${"  "*depth}")
  }

  def addProperty(property: Property, keys: List[String] = List()): ObjectValue = {
    // Add way to add at an index.
    if (keys.isEmpty) ObjectValue(properties :+ property)
      // We need to check if it valid
    else {
      val current = keys.head
      val next = keys.tail
      val (before, after) = properties.span(p => p.key != current)
      if (after.isEmpty) this // What do we do if it can't be added?
      else {
        after.head.value match {
          case value: ObjectValue =>
            ObjectValue((before :+ Property(current, value.addProperty(property, next))) ++ after.tail)
          case _ => this
        }
      }
    }
  }

  def addPropertyAtIndex(property: Property, index: Int, keys: List[String] = List()): ObjectValue = {
    if (keys.isEmpty) {
      val (before, after) = properties.splitAt(index) // Check how this works?
      ObjectValue((before :+ property) ++ after)
    }
    // We need to check if it valid
    else {
      val current = keys.head
      val next = keys.tail
      val (before, after) = properties.span(p => p.key != current)
      if (after.isEmpty) this // What do we do if it can't be added?
      else {
        after.head.value match {
          case value: ObjectValue =>
            ObjectValue((before :+ Property(current, value.addPropertyAtIndex(property, index, next))) ++ after.tail)
          case _ => this
        }
      }
    }
  }

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
    case value: JsNumber => NumberValue(value.value.doubleValue)
    case value: JsBoolean => BooleanValue(value.value)
    // Consider other types.
  }
}
