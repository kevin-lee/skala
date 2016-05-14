package io.kevinlee

import scala.util.{Failure, Success, Try}

/**
  * @author Kevin Lee
  * @since 2016-05-08
  */
package object skala {

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
    * @param closeable The given [[java.lang.AutoCloseable]] object which is used by the given function.
    * @param f         The function which does actual work before [[java.lang.AutoCloseable#close()]] is called.
    * @tparam T        Any [[java.lang.AutoCloseable]] type
    * @tparam R        The type of the result returned by the given function f.
    * @return          The result from the given function f if it succeeds or it may throw an exception when it fails.
    * @throws          Throwable when any Throwable was thrown. It depends on the given closeable or the function f.
    */
  def tryWith[T <: AutoCloseable, R](closeable: => T)(f: T => R): R =
    Try(closeable) map { resource =>
      try {
        f(resource)
      } finally {
        Try(resource.close()) match {
          case Failure(ex) =>
            println(ex)
          case _ =>
        }
      }
    } match {
      case Success(r) => r
      case Failure(ex) => throw ex
    }

}
