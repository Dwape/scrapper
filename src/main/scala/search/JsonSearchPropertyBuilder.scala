package search

case class JsonSearchPropertyBuilder(keys: Seq[String] = Seq()) {

  def field(key: String): JsonSearchPropertyBuilder = {
    JsonSearchPropertyBuilder(keys :+ key)
  }

  def find(key: String, finder: Finder): JsonSearchProperty = {
    JsonSearchProperty(keys :+ key, finder)
  }
}
