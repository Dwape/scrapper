package structure

case class ArrayValue(values: List[Value]) extends Value {
  override def parse(): String = {
    values
      .foldLeft("[")((acc, current) => s"$acc${current.parse()},").dropRight(1)+"]" // Do we need to add new lines?
  }
  override def depthParse(depth: Int = 0): String = {
    values
      .foldLeft("[")((acc, current) => s"$acc${current.depthParse(depth+1)},")
      .dropRight(1)+"]" // Do we need to add new lines?
  }
}
