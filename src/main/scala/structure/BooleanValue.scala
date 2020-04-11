package structure

case class BooleanValue(value: Boolean) extends LeafValue {
  override def parse(): String = s"""$value"""

  override def depthParse(depth: Int): String = parse()
}
