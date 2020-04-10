package response

import response.error.Error

case class FailedResponse[T](error: Error) extends Response[T] {
  override def isSuccess: Boolean = false
}
