package cc.kevinlee.skala

import java.lang.{Math => JavaMath}

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
package object math {
  def isOdd(number: Int):Boolean = (number & 1) != 0
  def isEven(number: Int):Boolean = !isOdd(number)

  def sqrt(number: Int): Double = JavaMath.sqrt(number.toDouble)
  def sqrt(number: Long): BigDecimal = BigInts.sqrt(number)
}
