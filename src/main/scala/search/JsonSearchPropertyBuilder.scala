package search

case class JsonSearchPropertyBuilder(keys: Seq[String] = Seq()) {

  def field(key: String): JsonSearchPropertyBuilder = {
    JsonSearchPropertyBuilder(keys :+ key)
  }

  def find(key: String): JsonSearchProperty = {
    JsonSearchProperty(keys :+ key)
  }
}
