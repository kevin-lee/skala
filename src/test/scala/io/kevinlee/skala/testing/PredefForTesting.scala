package io.kevinlee.skala.testing

import org.scalactic.TripleEqualsSupport.Spread
import org.scalatest.{Assertion, Matchers}

/**
  * @author Kevin Lee
  * @since 2018-04-18
  */
object PredefForTesting extends Matchers {

  final case class Approx[A](value: Spread[A]) extends AnyVal

  def approximately[A](approx: Spread[A]): Approx[A] = Approx(approx)

  implicit class OptionAssert[A](val actual: Option[A]) extends AnyVal {
    def shouldHave(expected: A): Assertion  =
      actual match {
        case Some(actualValue) =>
          actualValue should be (expected)
        case None =>
          fail(s"actual must be Some($expected) but None is found")
      }

    def shouldHave(expected: Approx[A]): Assertion  =
      actual match {
        case Some(actualValue) =>
          actualValue should be (expected.value)
        case None =>
          fail(s"actual must be Some(${expected.value}) but None is found")
      }
  }

}
