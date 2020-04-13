package search

/**
 * Builds a JsonSearchProperty.
 * The outer keys for the field are added individually with the field method.
 * This must be added in order, starting from the outside.
 *
 * The name of the field whose value will be returned must be provided to the find method.
 */
case class JsonSearchPropertyBuilder(keys: Seq[String] = Seq()) {

  def field(key: String): JsonSearchPropertyBuilder = {
    JsonSearchPropertyBuilder(keys :+ key)
  }

  def find(key: String, finder: Finder): JsonSearchProperty = {
    JsonSearchProperty(keys :+ key, finder)
  }
}
