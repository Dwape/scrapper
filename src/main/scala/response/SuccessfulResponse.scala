package response

case class SuccessfulResponse[T](result: T) extends Response[T] {
  override def isSuccess: Boolean = true
}
