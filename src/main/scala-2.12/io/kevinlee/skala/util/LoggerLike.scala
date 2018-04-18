package io.kevinlee.skala.util

trait LoggerLike {
  def log(throwable: Throwable): Unit
}

object LoggerLike {
  def someLogger(f: Throwable => Unit): LoggerLike = (throwable: Throwable) => f(throwable)

  val dummyLogger: LoggerLike = someLogger(_ => ())
  val printlnLogger: LoggerLike = someLogger(println(_))
}