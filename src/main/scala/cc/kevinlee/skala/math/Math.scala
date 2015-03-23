package cc.kevinlee.skala.math

import scala.annotation.tailrec
import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object Math {
  import collection.immutable.Seq

  def abs(x: BigDecimal): BigDecimal = if (x < 0) -x else x
  def absBigInt(x: BigInt): BigInt = if (x < 0) -x else x

  def isOdd(number: Int):Boolean = (number & 1) != 0
  def isEven(number: Int):Boolean = !isOdd(number)

  private def isGoodEnough(guess: BigDecimal, x: BigDecimal): Boolean = abs(guess * guess - x) / x < 1E-30
  private def improve(guess: BigDecimal, x: BigDecimal): BigDecimal = (guess + x / guess) / 2

  @tailrec
  private[math] def sqrtIter(guess: BigDecimal, x: BigDecimal): BigDecimal =
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)

  def sqrt(x: BigDecimal): BigDecimal = sqrtIter(1, x)
  def sqrtBigInt(x: BigInt): BigInt = sqrtIter(1, BigDecimal(x)).toBigInt()

  implicit class MathBigDecimal(number: BigDecimal) {
    def sqrt(): BigDecimal = Math.sqrt(number)
  }

  implicit class MathBigInt(number: BigInt) {
    def sqrt(): BigInt = sqrtBigInt(number)
  }

  def calcMean(numbers: TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]):BigDecimal = if (numbers.isEmpty) 0 else numbers.sum / numbers.size

  def calcBigIntMean(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]):BigDecimal = if (numbers.isEmpty) 0 else BigDecimal(numbers.sum) / numbers.size

  def calcMedian(sortedNumbers: Seq[BigDecimal], length: Int):BigDecimal = length match {
    case 0 => 0
    case theLength if (theLength & 1) != 1 =>
      val half = theLength / 2
      (sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength => sortedNumbers(theLength / 2)
  }
  def calcMedian(numbers: Seq[BigDecimal]):BigDecimal = calcMedian(numbers.sortBy(identity), numbers.length)

  def calcBigIntMedian(sortedNumbers: Seq[BigInt], length: Int):BigDecimal = length match {
    case 0 => 0
    case theLength if isEven(theLength) =>
      val half = theLength / 2
      BigDecimal(sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength => BigDecimal(sortedNumbers(theLength / 2))
  }
  def calcBigIntMedian(numbers: Seq[BigInt]):BigDecimal = calcBigIntMedian(numbers.sortBy(identity), numbers.length)

  def calcMode(sortedNumbers: Seq[BigDecimal]): BigDecimal = if (sortedNumbers.isEmpty) 0 else sortedNumbers.groupBy(identity).maxBy(_._2.length)._1

  def calcBigIntMode(sortedNumbers: Seq[BigInt]): BigInt = if (sortedNumbers.isEmpty) 0 else sortedNumbers.groupBy(identity).maxBy(_._2.length)._1

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

  def calcBigIntStandardDeviation(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]], length: Int, mean: BigDecimal): BigDecimal =
    if (length == 0)
      0
    else
      sqrt(
          numbers.map(BigDecimal(_) - mean)
               .map(x => x * x)
               .sum / length
      )

  def calcBigIntStandardDeviation(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]): BigDecimal = calcBigIntStandardDeviation(numbers, numbers.size, calcBigIntMean(numbers))

  implicit class BigDecimals (numbers: Seq[BigDecimal]) {
    def sortedNumbers = numbers.sortBy(identity)
    def mean = calcMean(numbers)
    def median = calcMedian(sortedNumbers, numbers.length)
    def mode = calcMode(sortedNumbers)
    def standardDeviation = calcStandardDeviation(numbers, numbers.length, mean)
  }

  implicit class BigInts (numbers: Seq[BigInt]) {
    def sortedNumbers = numbers.sortBy(identity)
    def mean = calcBigIntMean(numbers)
    def median = calcBigIntMedian(sortedNumbers, numbers.length)
    def mode = calcBigIntMode(sortedNumbers)
    def standardDeviation = calcBigIntStandardDeviation(numbers, numbers.length, mean)
  }

}
