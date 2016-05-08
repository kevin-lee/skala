package io.kevinlee.skala

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
package object math {

  implicit class MathInt(val number: Int) extends AnyVal {
    def isOdd: Boolean = CommonMath.isOdd(number)
    def isEven: Boolean = CommonMath.isEven(number)
    def sqrt: Double = CommonMath.sqrt(number)
    def toOrdinal: String = CommonMath.toOrdinal(number)
    def findOrdinal: Option[String] = CommonMath.findOrdinal(number)
  }

  implicit class MathLong(val number: Long) extends AnyVal {
    def isOdd: Boolean = CommonMath.isOdd(number)
    def isEven: Boolean = CommonMath.isEven(number)
    def sqrt: BigDecimal = CommonMath.sqrt(number)
    def toOrdinal: String = CommonMath.toOrdinal(number)
    def findOrdinal: Option[String] = CommonMath.findOrdinal(number)
  }
}
