package io.kevinlee.skala.util

import scala.util.Try

/**
  * @author Kevin Lee
  * @since 2017-03-07
  */
object TryWith {

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
  def apply[T <: AutoCloseable, R](closeable: => T)(
                                   f: T => R)(
                                   implicit closeFailureLogger: LoggerLike = LoggerLike.dummyLogger): Try[R] = {
    lazy val resource = Try(closeable)
    try {
      resource.map(f)
    } finally {
      resource.foreach { x =>
        try {
          x.close()
        } catch {
          case ex: Throwable => closeFailureLogger.log(ex)
        }
      }
    }
  }

  object SideEffect {

    /**
      * runs the given function with the given [[java.lang.AutoCloseable]] param then calls the [[java.lang.AutoCloseable#close()]].
      *
      * It is design to dispose the resource after it is used.
      * Reading and writing might be good examples of when this tryWith can be useful.
      *
      * @example
      * {{{
      * tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *   var c = inputStream.read()
      *   while (c != -1) {
      *     print(c.toChar)
      *     c = inputStream.read()
      *   }
      * }
      * }}}
      *
      * {{{
      *
      * // content == result
      * val content = tryWith(new FileInputStream("/tmp/kevin-test/old.csv")) { inputStream =>
      *   var c = inputStream.read()
      *   var result = Vector(c.toChar)
      *   while (c != -1) {
      *     c = inputStream.read()
      *     result = result:+ c.toChar
      *   }
      *   result
      * }
      * }}}
      *
      * Or a better example might be
      * {{{
      * // content is Success(Vector) when it succeeds or Failure(Throwable) if it fails.
      * val content = Try(tryWith(new FileInputStream("/tmp/kevin-test/old.csv")) { inputStream =>
      *   var c = inputStream.read()
      *   var result = Vector(c.toChar)
      *   while (c != -1) {
      *     c = inputStream.read()
      *     result = result:+ c.toChar
      *   }
      *   result
      * })
      * }}}
      *
      * {{{
      * tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *   tryWith(new InputStreamReader(inputStream)) { reader =>
      *
      *     var c = reader.read()
      *     while (c != -1) {
      *       print(c.toChar)
      *       c = reader.read()
      *     }
      *   }
      * }
      * }}}
      *
      * {{{
      * // content == result
      * val content = tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *
      *   tryWith(new InputStreamReader(inputStream)) { reader =>
      *
      *     var c = reader.read()
      *     var result = Vector(c.toChar)
      *     while (c != -1) {
      *       c = inputStream.read()
      *       result = result:+ c.toChar
      *     }
      *     result
      *   }
      * }
      * }}}
      *
      * Or a better example might be
      * {{{
      * // content is Success(Vector) when it succeeds or Failure(Throwable) if it fails.
      * val content = Try(tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *
      *   tryWith(new InputStreamReader(inputStream)) { reader =>
      *
      *     var c = reader.read()
      *     var result = Vector(c.toChar)
      *     while (c != -1) {
      *       c = inputStream.read()
      *       result = result:+ c.toChar
      *     }
      *     result
      *   }
      * })
      * }}}
      *
      * <br />
      * To handle an exception thrown when closing the resource, use closeFailureLogger: LoggerLike
      * <br />
      * To log it, use some logging framework like
      * {{{
      * implicit private val logger = LoggerLike.someLogger(ex => logger.debug(ex))
      *
      * // or if you don't want any logging framework but a simple println
      * implicit private val printlnLogger = io.kevinlee.skala.util.TryWith.LoggerLike#printlnLogger
      *
      * // or if you don't want to log anything, use this dummy logger
      * implicit private val dummyLogger = io.kevinlee.skala.util.TryWith.LoggerLike#dummyLogger
      * }}}
      * @param closeable           The given [[java.lang.AutoCloseable]] object which is used by the given function.
      * @param f                   The function which does actual work before [[java.lang.AutoCloseable#close()]] is called.
      * @param closeFailureLogger The given function to handle an error properly when close throws an exception.
      * @tparam T Any [[java.lang.AutoCloseable]] type
      * @tparam R The type of the result returned by the given function f.
      * @return The result from the given function f if it succeeds or it may throw an exception when it fails.
      * @throws          Throwable when any Throwable was thrown. It depends on the given closeable or the function f.
      */
    @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
    def tryWith[T <: AutoCloseable, R](closeable: => T)(
                                       f: T => R)(
                                       implicit closeFailureLogger: LoggerLike = LoggerLike.dummyLogger): R = {
      lazy val resource = closeable
      try {
        f(resource)
      } finally {
        try {
          resource.close()
        } catch {
          case ex: Throwable => closeFailureLogger.log(ex)
        }
      }
    }

  }
}
