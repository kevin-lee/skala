package cc.kevinlee.skala

import java.lang.{Math => JavaMath}

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
package object math {
  def isOdd(number: Int):Boolean = (number & 1) != 0
  def isEven(number: Int):Boolean = !isOdd(number)

  def isOdd(number: Long):Boolean = (number & 1) != 0
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
  def findOrdinal(number: Int): Option[String] = Option(number).map(toOrdinal).filterNot(_.isEmpty)

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
  def findOrdinal(number: Long): Option[String] = Option(number).map(toOrdinal).filterNot(_.isEmpty)

  implicit class MathInt(val number: Int) extends AnyVal {
    def isOdd: Boolean = math.isOdd(number)
    def isEven: Boolean = math.isEven(number)
    def sqrt: Double = math.sqrt(number)
    def toOrdinal: String = math.toOrdinal(number)
    def findOrdinal: Option[String] = math.findOrdinal(number)
  }

  implicit class MathLong(val number: Long) extends AnyVal {
    def isOdd: Boolean = math.isOdd(number)
    def isEven: Boolean = math.isEven(number)
    def sqrt: BigDecimal = math.sqrt(number)
    def toOrdinal: String = math.toOrdinal(number)
    def findOrdinal: Option[String] = math.findOrdinal(number)
  }
}
