package io.kevinlee.skala.util

import scala.util.Try

/**
  * @author Kevin Lee
  * @since 2017-03-07
  */
object TryWith {

  type CloseFailureHandler = Throwable => Unit

  val dummyLogger: CloseFailureHandler = _ => ()

  def apply[T <: AutoCloseable, R](closeable: => T)(
                                   f: T => R)(
                                   implicit closeFailureHandler: CloseFailureHandler = dummyLogger): Try[R] = {
    lazy val resource = Try(closeable)
    try {
      resource.map(f)
    } finally {
      resource.map{ x =>
        try {
          x.close()
        } catch {
          case ex: Throwable => closeFailureHandler(ex)
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
      * TryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
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
      * val content = TryWith(new FileInputStream("/tmp/kevin-test/old.csv")) { inputStream =>
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
      * val content = Try(TryWith(new FileInputStream("/tmp/kevin-test/old.csv")) { inputStream =>
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
      * TryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *   TryWith(new InputStreamReader(inputStream)) { reader =>
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
      * val content = TryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *
      *   TryWith(new InputStreamReader(inputStream)) { reader =>
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
      * val content = Try(TryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
      *
      *   TryWith(new InputStreamReader(inputStream)) { reader =>
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
      * To handle an exception thrown when closing the resource, use closeFailureHandler: Throwable => Unit
      * <br />
      * To log it, use some logging framework like
      * {{{
      * implicit private val logger = (ex: Throwable) => logger.debug(ex)
      *
      * // or if you don't want any logging framework but a simple println
      * io.kevinlee.skala.util.TryWith.printlnLogger
      *
      * // or if you don't want to log anything, use this dummy logger
      * io.kevinlee.skala.util.TryWith.dummyLogger
      * }}}
      * @param closeable           The given [[java.lang.AutoCloseable]] object which is used by the given function.
      * @param f                   The function which does actual work before [[java.lang.AutoCloseable#close()]] is called.
      * @param closeFailureHandler The given function to handle an error properly when close throws an exception.
      * @tparam T Any [[java.lang.AutoCloseable]] type
      * @tparam R The type of the result returned by the given function f.
      * @return The result from the given function f if it succeeds or it may throw an exception when it fails.
      * @throws          Throwable when any Throwable was thrown. It depends on the given closeable or the function f.
      */
    def tryWith[T <: AutoCloseable, R](closeable: => T)(
                                       f: T => R)(
                                       implicit closeFailureHandler: CloseFailureHandler = dummyLogger): R = {
      lazy val resource = closeable
      try {
        f(resource)
      } finally {
        try {
          resource.close()
        } catch {
          case ex: Throwable => closeFailureHandler(ex)
        }
      }
    }

  }
}
