package structure

import org.jsoup.nodes.Document

/**
 * Represents a Json array.
 * @param values The values inside the array.
 */
case class ArrayValue(values: List[Value]) extends Value {
  override def parse()(implicit document: Document): String = {
    values
      .foldLeft("[")((acc, current) => s"$acc${current.parse()},").dropRight(1)+"]" // Do we need to add new lines?
  }
  override def depthParse(depth: Int = 0)(implicit document: Document): String = {
    values
      .foldLeft("[")((acc, current) => s"$acc${current.depthParse(depth+1)},")
      .dropRight(1)+"]" // Do we need to add new lines?
  }
}
