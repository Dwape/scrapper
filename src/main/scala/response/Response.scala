package response

trait Response[T] {
  def isSuccess: Boolean
  def isFailure: Boolean = !isSuccess
}
