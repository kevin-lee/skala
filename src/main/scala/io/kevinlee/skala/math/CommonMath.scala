package io.kevinlee.skala.math

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-07-18
 */
object CommonMath {
  import io.kevinlee.skala.SkalaPredef.AnyEquals

  def isOdd(number: Int):Boolean = (number & 1) !== 0
  def isEven(number: Int):Boolean = !isOdd(number)

  def isOdd(number: Long):Boolean = (number & 1) !== 0
  def isEven(number: Long):Boolean = !isOdd(number)

  def sqrt(number: Int): Double = scala.math.sqrt(number.toDouble)
  def sqrt(number: Long): BigDecimal = BigInts.sqrt(number)

  def toOrdinal(number: Int): String = number match {
    case (11 | 12 | 13) => s"${number}th"
    case _ if number > 0 => number % 10 match {
      case 1 => s"${number}st"
      case 2 => s"${number}nd"
      case 3 => s"${number}rd"
      case _ => s"${number}th"
    }
    case _ => ""
  }
  def findOrdinal(number: Int): Option[String] = Option(number).map(toOrdinal).filter(_.nonEmpty)

  def toOrdinal(number: Long): String = number match {
    case (11 | 12 | 13) => s"${number}th"
    case _ if number > 0 => number % 10 match {
      case 1 => s"${number}st"
      case 2 => s"${number}nd"
      case 3 => s"${number}rd"
      case _ => s"${number}th"
    }
    case _ => ""
  }
  def findOrdinal(number: Long): Option[String] = Option(number).map(toOrdinal).filter(_.nonEmpty)

  def mode[T](numbers: Seq[T])(implicit cmp: Ordering[T]): Seq[T] =
    if (numbers.isEmpty)
      Nil
    else {
      val grouped = numbers.groupBy(identity)
                           .map { case (n, ns) => (n, ns.length) }
                           .groupBy(_._2)
      val max = grouped.maxBy(_._1)._1
      grouped(max).keySet.toList.sorted
    }

}
