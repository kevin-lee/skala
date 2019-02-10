package io.kevinlee.skala.util

/**
  * @author Kevin Lee
  * @since 2019-02-08
  */
trait CanClose[A] {
  def close(a: A): Unit
}

object CanClose {
  implicit def CanCloseAutoCloseable[A <: AutoCloseable]: CanClose[A] = new CanClose[A] {
    def close(a: A): Unit = a.close()
  }
}
