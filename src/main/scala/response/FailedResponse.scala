package response

import response.error.Error

/**
 * Represents an unsuccessful response.
 * @param error An error explaining why the operation failed.
 * @tparam T The response content's type.
 */
case class FailedResponse[T](error: Error) extends Response[T] {
  override def isSuccess: Boolean = false
}
