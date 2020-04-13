package response

/**
 * A generic response returned by different operations.
 * @tparam T The response content's type.
 */
trait Response[T] {
  def isSuccess: Boolean
  def isFailure: Boolean = !isSuccess
}
