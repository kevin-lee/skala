package io.kevinlee

import scala.util.{Failure, Success, Try}

/**
  * @author Kevin Lee
  * @since 2016-05-08
  */
package object skala {

  /**
    * runs the given function with the given AuthoCloseable param then calls the AuthoCloseable.close().
    *
    * It is design to dispose the resource after it is used.
    * Reading and writing might be good examples of when this tryWith can be useful.
    *
    * @example
    * {{{
    * tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
    *   var c = inputStream.read()
    *   while (c != -1) {
    *     print(c.asInstanceOf[Char])
    *     c = inputStream.read()
    *   }
    * }
    * }}}
    *
    * {{{
    * tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
    *   tryWith(new InputStreamReader(inputStream)) { reader =>
    *
    *     var c = reader.read()
    *     while (c != -1) {
    *       print(c.asInstanceOf[Char])
    *       c = reader.read()
    *     }
    *   }
    * }
    * }}}
    * @param closeable the given AutoCloseable object which is used by the given function.
    * @param f         the function which does actual work after AuthoCloseable.close() is called.
    * @tparam T Any AuthoCloseable type
    * @tparam R The result returned by the given function f.
    * @return Try[R] containing the result from the given function f if it succeeds or Throwable when it fails.
    */
  def tryWith[T <: AutoCloseable, R](closeable: => T)(f: T => R): Try[R] = {
    Try(closeable) match {
      case Success(resource) =>
        val result = Try(f(resource))

        Try(resource.close()) match {
          case Failure(ex) => println(ex)
          case _ =>
        }
        result

      case failure =>
        failure.asInstanceOf[Try[R]]
    }
  }

}
