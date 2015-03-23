package cc.kevinlee.skala

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
package object math {
  def isOdd(number: Int):Boolean = (number & 1) != 0
  def isEven(number: Int):Boolean = !isOdd(number)
}
