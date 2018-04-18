package io.kevinlee.skala

/**
  * @author Kevin Lee
  * @since 2018-04-17
  */
object SkalaPredef {

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  implicit final class AnyEquals[A](val self: A) extends AnyVal {
    def ===(other: A): Boolean = self == other
    def !==(other: A): Boolean = self != other
  }

}
