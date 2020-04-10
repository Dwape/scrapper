package structure

case class ObjectValue(properties: List[Property]) extends Value {
  override def parse(): String = {
    properties
      .foldLeft("{")((acc, current) => s"$acc${current.parse()},").dropRight(1)+"}" // Do we need to add new lines?
  }
}
