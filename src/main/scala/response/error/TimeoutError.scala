package response.error

/**
 * An error returned when the fetcher times out trying to fetch a website.
 * @param millis The timeout.
 */
case class TimeoutError(millis: Int) extends Error {
  override def message: String = s"Connection timed out after $millis milliseconds"
}
