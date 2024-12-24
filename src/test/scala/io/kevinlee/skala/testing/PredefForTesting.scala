package io.kevinlee.skala.testing

import hedgehog._

/**
  * @author Kevin Lee
  * @since 2018-04-18
  */
object PredefForTesting {

  final case class Approx[A: Numeric](a: A, tolerance: A)
  object Approx {
    def isApproximately[A: Numeric](a: A, approx: Approx[A]): Boolean =
      Numeric[A].gteq(a, Numeric[A].minus(approx.a, approx.tolerance)) &&
        Numeric[A].lteq(a, Numeric[A].plus(approx.a, approx.tolerance))

    @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
    def render[A: Numeric](approx: Approx[A]): String =
      s"${Numeric[A].minus(approx.a, approx.tolerance)} <= n <= ${Numeric[A].plus(approx.a, approx.tolerance)}"

    @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
    def renderMessage[A: Numeric](approx: Approx[A]): String =
      s"${Numeric[A].minus(approx.a, approx.tolerance)} ~ ${Numeric[A].plus(approx.a, approx.tolerance)}"
  }

  implicit class ToApprox[A](val a: A) extends AnyVal {
    def +-(tolerance: A)(implicit N: Numeric[A]): Approx[A] =
      Approx(a, tolerance)
  }

  implicit class ResultApproximately[A](val a: A) extends AnyVal {
    @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
    def isApproximately(approx: Approx[A])(implicit N: Numeric[A]): Result =
      Result.diffNamed(
        s"$a is approximately ${Approx.renderMessage(approx)}",
        a,
        approx
      )(Approx.isApproximately)
  }

  implicit class OptionAssert[A](val actual: Option[A]) extends AnyVal {
    @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
    def shouldHave(expected: A): Result =
      actual match {
        case Some(actualValue) =>
          actualValue ==== expected
        case None =>
          Result.failure.log(s"actual must be Some($expected) but None is found")
      }

    def shouldHaveApproximately(expected: Approx[A])(implicit N: Numeric[A]): Result  =
      actual match {
        case Some(actualValue) =>
          actualValue isApproximately expected
        case None =>
          Result.failure.log(s"actual must be Some(${Approx.render(expected)}) but None is found")
      }
  }

}
