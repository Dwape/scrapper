package response.error

case class NoParserFoundError(url: String) extends Error {
  override def message: String = s"No parser found for $url"
}
