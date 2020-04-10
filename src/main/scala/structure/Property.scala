package structure

case class Property(key: String, value: Value) extends Serializable {
  override def parse(): String = s""""$key": ${value.parse()}"""
}
