package response.error

case class InvalidURLError(url: String) extends Error {
  override def message: String = s"URL $url is malformed"
}
