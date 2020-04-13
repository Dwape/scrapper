package response.error

/**
 * Represents a generic error in the execution of the Scrapper.
 */
trait Error {
  /**
   * Returns the error message.
   * If the error contains no message, "No message available" will be returned.
   */
  def message: String
}
