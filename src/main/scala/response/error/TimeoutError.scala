package response.error

case class TimeoutError(millis: Int) extends Error {
  override def message: String = s"Connection timed out after $millis milliseconds"
}
