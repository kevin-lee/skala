package cc.kevinlee.skala.strings

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-05-17
 */
object StringGlues {

  type Glue = (String, String) => String

  def glue(separator: String): Glue = glueWith(separator)_

  private def glueWith(separator: String)(path1: String, path2: String): String =
    if (path1 endsWith separator) {
      if (path2 startsWith separator) path1 + path2.substring(1)
      else path1 + path2
    }
    else {
      if (path2 startsWith separator) path1 + path2
      else path1 + separator + path2
    }

  val glueWithSlash: Glue = glue("/")
  val glueWithBackSlash: Glue = glue("\\")

  implicit class PathString(val path: String) extends AnyVal {

    def /(path2: String): String = glueWithSlash(path, path2)

    def \(path2: String): String = glueWithBackSlash(path, path2)
  }

}
