package response

/**
 * Represents a successful response.
 * @param result The content of the response
 * @tparam T The response content's type.
 */
case class SuccessfulResponse[T](result: T) extends Response[T] {
  override def isSuccess: Boolean = true
}
