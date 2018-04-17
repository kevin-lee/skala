package io.kevinlee.skala

/**
  * @author Kevin Lee
  * @since 2018-04-17
  */
object Predef {

  implicit final class AnyEquals[A](val self: A) extends AnyVal {
    def ===(other: A): Boolean = self == other
    def !==(other: A): Boolean = self != other
  }

}
