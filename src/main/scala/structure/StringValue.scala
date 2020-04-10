package structure

case class StringValue(value: String) extends Value {
  override def parse(): String = s""""$value""""
}
