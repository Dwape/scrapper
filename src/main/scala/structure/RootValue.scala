package structure

// This will probably not be needed.
case class RootValue(value: Value) extends Value {
  // Doesn't add anything.
  override def parse(): String = value.parse()
  override def depthParse(depth: Int = 0): String = parse()
}
