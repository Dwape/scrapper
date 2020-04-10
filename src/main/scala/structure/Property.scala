package structure

case class Property(key: String, value: Value) extends Serializable {
  override def parse(): String = s""""$key": ${value.parse()}"""
  override def depthParse(depth: Int = 0): String = {
    s"""${"  "*depth}"$key": ${value.depthParse(depth)}"""
  }
}
