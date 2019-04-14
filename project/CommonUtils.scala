import java.io.{File => JFile, FileFilter => JFileFilter}

import org.apache.commons.io.filefilter.WildcardFileFilter
import sbt._

import scala.annotation.tailrec

object CommonUtils {

  def versionWriter(paramsResolver: () => Seq[String])(projectVersion: String, basePath: String = "target"): Unit = {
    println("\n== Writing Version File ==")
    val args: Seq[String] = paramsResolver()
    println(s"The project version is $projectVersion.")

    import IO._

    val filename = args.headOption.map(Prefix(basePath) + _).getOrElse("target/version.tmp")
    val versionFile = new sbt.File(filename)
    println(s"write $projectVersion into the file: $versionFile")

    write(versionFile, projectVersion, utf8, append = false)
    println("Done: Writing Version File\n")
  }

  def wildcardFilter(names: Seq[String]): JFileFilter =
    new WildcardFileFilter(names.toArray).asInstanceOf[JFileFilter]


  //  def getAllSubDirs(dir: File): Array[File] = dir.listFiles(DirectoryFilter).flatMap(x => x +: getAllSubDirs(x))
  def getAllSubDirs(dir: File): Seq[File] = {
    @tailrec
    def getAllSubDirs(dirs: Array[File],
                      acc: Vector[File]): Vector[File] = dirs match {
      case Array() =>
        acc
      case Array(x) =>
        getAllSubDirs(x.listFiles(DirectoryFilter), acc :+ x)
      case array @ Array(x, _*) =>
        getAllSubDirs(array.drop(1) ++ x.listFiles(DirectoryFilter), acc :+ x)
    }
    getAllSubDirs(dir.listFiles(DirectoryFilter), Vector.empty)
    //    dir.listFiles(DirectoryFilter).flatMap(x => x +: getAllSubDirs(x))
  }

  object BasePath {
    def apply(base: File, dir: String): BasePath = BasePath(base, Option(dir))
    def apply(base: File): BasePath = BasePath(base, None)
  }

  case class BasePath(base: File, dir: Option[String] = None) {
    lazy val pathLength: Int = {
      val thePath = base / dir.getOrElse("")
      val basePath = thePath.getPath
      basePath.length + (if (basePath.endsWith(JFile.separator)) 0 else 1)
    }
  }

  object Prefix {
    def apply(value: String): Prefix = Option(value).fold[Prefix](NoPrefix)(PrefixVal)
    def apply(): Prefix = NoPrefix
  }

  sealed trait Prefix {
    def value: String
    def isEmpty: Boolean
    def fold(defaultVal: => String)(f: String => String): String = if (isEmpty) defaultVal else f(value)
    def +(path: => String): String = fold(path)(prefix => s"$prefix/$path")
  }

  case object NoPrefix extends Prefix { val isEmpty = true; val value = "" }
  private case class PrefixVal(value: String) extends Prefix { val isEmpty = false }

}