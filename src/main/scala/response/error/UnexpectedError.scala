package response.error

/**
 * Returned when there is an exception that was not considered in the expected cases.
 * @param t The exception that was thrown.
 */
case class UnexpectedError(t: Throwable) extends Error {
  /**
   * Returns the error message.
   */
  override def message: String = {
    val message = t.getMessage
    if (message != null) message
    else "No message available"
  }
}
