package structure

case class RootValue(value: Value) extends Value {
  // Doesn't add anything.
  override def parse(): String = value.parse()
}
