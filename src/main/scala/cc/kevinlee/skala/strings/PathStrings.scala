package cc.kevinlee.skala.strings

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-05-17
 */
object PathStrings {

  implicit class PathString(val path: String) extends AnyVal {
    def /(path2: String): String = concat(path, path2, "/")

    def \(path2: String): String = concat(path, path2, "\\")

    private def concat(path1: String, path2: String, withSeparator: String): String =
      if (path1.endsWith(withSeparator)) {
        if (path2.startsWith(withSeparator)) path1 + path2.substring(1)
        else path1 + path2
      }
      else {
        if (path2.startsWith(withSeparator)) path1 + path2
        else path1 + withSeparator + path2
      }
  }

}
