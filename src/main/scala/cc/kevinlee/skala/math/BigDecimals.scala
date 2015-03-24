package cc.kevinlee.skala.math

import scala.annotation.tailrec
import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object BigDecimals {
  import collection.immutable.Seq

  def abs(x: BigDecimal): BigDecimal = if (x < 0) -x else x

  private def isGoodEnough(guess: BigDecimal, x: BigDecimal): Boolean = abs(guess * guess - x) / x < 1E-30
  private def improve(guess: BigDecimal, x: BigDecimal): BigDecimal = (guess + x / guess) / 2

  private[math] def sqrt(guess: BigDecimal, x: BigDecimal): BigDecimal = {
    @tailrec
    def sqrtIter(guess: BigDecimal, x: BigDecimal): BigDecimal =
      if (isGoodEnough(guess, x)) guess
      else sqrtIter(improve(guess, x), x)

    if (x == 0) BigDecimal(0) else sqrtIter(guess, x)
  }

  def sqrt(x: BigDecimal): BigDecimal = if (x < 0) throw new IllegalArgumentException(s"\u221A of negative number! sqrt can handle only non-negative numbers. [entered: $x]") else sqrt(1, x)
  def findSqrt(x: BigDecimal): Option[BigDecimal] = if (x < 0) None else Option(sqrt(1, x))

  implicit class MathBigDecimal(number: BigDecimal) {
    def sqrt(): BigDecimal = BigDecimals.sqrt(number)
  }

  def calcMean(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]):BigDecimal = if (numbers.isEmpty) 0 else numbers.sum / numbers.size

  def calcMedian(sortedNumbers: Seq[BigDecimal], length: Int):BigDecimal = length match {
    case 0 => 0
    case theLength if (theLength & 1) != 1 =>
      val half = theLength / 2
      (sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength => sortedNumbers(theLength / 2)
  }
  def calcMedian(numbers: Seq[BigDecimal]):BigDecimal = calcMedian(numbers.sortBy(identity), numbers.length)

  def calcMode(sortedNumbers: Seq[BigDecimal]): BigDecimal = if (sortedNumbers.isEmpty) 0 else sortedNumbers.groupBy(identity).maxBy(_._2.length)._1

  def calcStandardDeviation(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]], length: Int, mean: BigDecimal): BigDecimal =
    if (length == 0)
      0
    else
      sqrt(
        numbers.map(_ - mean)
               .map(x => x * x)
               .sum / length
      )

  def calcStandardDeviation(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]): BigDecimal = calcStandardDeviation(numbers, numbers.size, calcMean(numbers))

  implicit class BigDecimalPlus (numbers: Seq[BigDecimal]) {
    def sortedNumbers = numbers.sortBy(identity)
    def mean = calcMean(numbers)
    def median = calcMedian(sortedNumbers, numbers.length)
    def mode = calcMode(sortedNumbers)
    def standardDeviation = calcStandardDeviation(numbers, numbers.length, mean)
  }


}
