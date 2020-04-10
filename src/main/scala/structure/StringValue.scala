package structure

case class StringValue(value: String) extends LeafValue {
  override def parse(): String = s""""$value""""

  override def depthParse(depth: Int = 0): String = parse()
}
