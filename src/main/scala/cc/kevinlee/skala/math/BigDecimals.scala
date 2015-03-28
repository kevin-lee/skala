package cc.kevinlee.skala.math

import java.math.{RoundingMode, MathContext}

import scala.annotation.tailrec
import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object BigDecimals {
  import collection.immutable.Seq

  private def isGoodEnough(guess: BigDecimal, number: BigDecimal): Boolean = ((guess * guess - number).abs / number) < 1E-32
  private def improve(guess: BigDecimal, number: BigDecimal): BigDecimal = (guess + number / guess) / 2

  private[math] def sqrt(guess: BigDecimal, number: BigDecimal): BigDecimal = {
    @tailrec
    def sqrtIter(guess: BigDecimal, number: BigDecimal): BigDecimal =
      if (isGoodEnough(guess, number)) guess
      else sqrtIter(improve(guess, number), number)

    if (number == 0) BigDecimal(0) else sqrtIter(guess, number)
  }

  /**
   * Returns the square root of a BigDecimal value.
   * @param number the given BigDecimal number
   * @return the positive square root of the given number.
   * @throws IllegalArgumentException If the argument is less than zero.
   */
  def sqrt(number: BigDecimal): BigDecimal = if (number < 0) throw new IllegalArgumentException(s"\u221A of negative number! sqrt can handle only non-negative numbers. [entered: $number]") else sqrt(1, number)

  def findSqrt(number: BigDecimal): Option[BigDecimal] = if (number < 0) None else Option(sqrt(1, number))

  implicit class MathBigDecimal(number: BigDecimal) {
    def sqrt(): BigDecimal = BigDecimals.sqrt(number)
  }

  def calcMean(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]):BigDecimal = if (numbers.isEmpty) 0 else numbers.sum / numbers.size

  def calcMedian(sortedNumbers: Seq[BigDecimal], length: Int):BigDecimal = length match {
    case 0 => 0
    case theLength if isEven(theLength) =>
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

  implicit class BigDecimalSeq(numbers: Seq[BigDecimal]) {
    def sortedNumbers = numbers.sortBy(identity)
    def mean = calcMean(numbers)
    def median = calcMedian(sortedNumbers, numbers.length)
    def mode = calcMode(sortedNumbers)
    def standardDeviation = calcStandardDeviation(numbers, numbers.length, mean)
  }


}
