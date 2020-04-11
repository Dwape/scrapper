package structure

case class NumberValue(value: Double) extends LeafValue { // Should this be a double?
  override def parse(): String = s"""$value"""

  override def depthParse(depth: Int = 0): String = parse()
}
