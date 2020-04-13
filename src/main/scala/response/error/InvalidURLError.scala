package response.error

/**
 * An error used when the URL provided is invalid and therefore the website cannot be fetched.
 * @param url The invalid url.
 */
case class InvalidURLError(url: String) extends Error {
  override def message: String = s"URL $url is malformed"
}
