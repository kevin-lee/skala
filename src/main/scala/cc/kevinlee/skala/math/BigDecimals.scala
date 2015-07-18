package cc.kevinlee.skala.math

import scala.annotation.tailrec
import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object BigDecimals {

  private def isGoodEnough(guess: BigDecimal, number: BigDecimal): Boolean = ((guess * guess - number).abs / number) < 1E-32
  private def improve(guess: BigDecimal, number: BigDecimal): BigDecimal = (guess + number / guess) / 2

  private[math] def sqrt(guess: BigDecimal, number: BigDecimal): BigDecimal = {
    @tailrec
    def sqrtIter(guess: BigDecimal, number: BigDecimal): BigDecimal =
      if (isGoodEnough(guess, number)) guess
      else sqrtIter(improve(guess, number), number)

    if ((number compare 0) == 0) BigDecimal(0) else sqrtIter(guess, number)
  }

  /**
   * Returns the square root of a BigDecimal value.
   * @param number the given BigDecimal number
   * @return the positive square root of the given number.
   * @throws IllegalArgumentException If the argument is less than zero.
   */
  def sqrt(number: BigDecimal): BigDecimal =
    if (number < 0) throw new IllegalArgumentException(s"\u221A of negative number! sqrt can handle only non-negative numbers. [entered: $number]")
    else sqrt(1, number)

  def findSqrt(number: BigDecimal): Option[BigDecimal] = if (number < 0) None else Option(sqrt(1, number))

  implicit class MathBigDecimal(val number: BigDecimal) extends AnyVal {
    def sqrt(): BigDecimal = BigDecimals.sqrt(number)
  }

  def mean(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]): BigDecimal = if (numbers.isEmpty) 0 else numbers.sum / numbers.size

  def median(sortedNumbers: Seq[BigDecimal], length: Int): BigDecimal = length match {
    case 0 => 0
    case theLength if isEven(theLength) =>
      val half = theLength / 2
      (sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength => sortedNumbers(theLength / 2)
  }
  def median(numbers: Seq[BigDecimal]): BigDecimal = median(numbers.sortBy(identity), numbers.length)

  def mode(numbers: Seq[BigDecimal]): Seq[BigDecimal] = CommonMath.mode(numbers)

  def standardDeviation(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]], length: Int, mean: BigDecimal): BigDecimal =
    if (length == 0)
      0
    else
      sqrt(
        numbers.map(_ - mean)
               .map(x => x * x)
               .sum / length
      )

  def standardDeviation(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]): BigDecimal = standardDeviation(numbers, numbers.size, mean(numbers))
  def stdev(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]): BigDecimal = standardDeviation(numbers)

  implicit class BigDecimalSeq(val numbers: Seq[BigDecimal]) extends AnyVal {
    def sortedNumbers: Seq[BigDecimal] = numbers.sortBy(identity)
    def mean: BigDecimal = BigDecimals.mean(numbers)
    def median: BigDecimal = BigDecimals.median(sortedNumbers, numbers.length)
    def mode: Seq[BigDecimal] = BigDecimals.mode(sortedNumbers)
    def standardDeviation: BigDecimal = BigDecimals.standardDeviation(numbers, numbers.length, mean)
  }


}
