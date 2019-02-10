package io.kevinlee.skala.util

/**
  * @author Kevin Lee
  * @since 2017-03-07
  */
object SideEffect {

  /**
    * runs the given function with the given takes a param of type A which has a type class [[io.kevinlee.skala.util.CanClose]] then returns B. After applying f to A and before returning the result it closes A using the type class  [[io.kevinlee.skala.util.CanClose]].
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
    * To handle an exception thrown when closing the resource, check out [[Throwable#getSuppressed]] as the exception thrown when closing is added to suppressed.
    * <br />
    *
    * @param a  Some type A used for the given function. There should be a type-class CanClose of A.
    * @param f  The function which does actual work before [[CanClose#close()]] is called.
    * @tparam A For A, type class CanClose[A] should exist
    * @tparam B The type of the result returned by the given function f.
    * @return The result from the given function f if it succeeds or it may throw an exception when it fails.
    * @throws Throwable when any Throwable was thrown. It depends on the given param, a or the function f or the type class CanClose[A].
    */
  @SuppressWarnings(Array(
    "org.wartremover.warts.Throw"
  , "org.wartremover.warts.Var"
  , "org.wartremover.warts.Equals"
  , "org.wartremover.warts.Null")
  )
  def tryWith[A : CanClose, B](a: => A)(f: A => B): B = {
    lazy val resource = a
    var firstEx: Throwable = null
    try {
      f(resource)
    } catch {
      case ex: Throwable =>
        firstEx = ex
        throw ex
    } finally {
      if (firstEx == null) {
        implicitly[CanClose[A]].close(resource)
      } else {
        try {
          implicitly[CanClose[A]].close(resource)
        } catch {
          case ex2: Throwable =>
            firstEx.addSuppressed(ex2)
            throw firstEx
        }
      }
    }
  }

}
