package cc.kevinlee.skala.math

import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object BigInts {
  import collection.immutable.Seq

  def abs(x: BigInt): BigInt = if (x < 0) -x else x

  def sqrt(x: BigInt): BigInt = BigDecimals.sqrtIter(1, BigDecimal(x)).toBigInt()


  implicit class MathBigInt(number: BigInt) {
    def sqrt(): BigInt = BigInts.sqrt(number)
  }

  def calcMean(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]):BigDecimal = if (numbers.isEmpty) 0 else BigDecimal(numbers.sum) / numbers.size


  def calcMedian(sortedNumbers: Seq[BigInt], length: Int):BigDecimal = length match {
    case 0 => 0
    case theLength if isEven(theLength) =>
      val half = theLength / 2
      BigDecimal(sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength => BigDecimal(sortedNumbers(theLength / 2))
  }

  def calcMedian(numbers: Seq[BigInt]):BigDecimal = calcMedian(numbers.sortBy(identity), numbers.length)

  def calcMode(sortedNumbers: Seq[BigInt]): BigInt = if (sortedNumbers.isEmpty) 0 else sortedNumbers.groupBy(identity).maxBy(_._2.length)._1


  def calcStandardDeviation(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]], length: Int, mean: BigDecimal): BigDecimal =
    if (length == 0)
      0
    else
      BigDecimals.sqrt(
        numbers.map(BigDecimal(_) - mean)
               .map(x => x * x)
               .sum / length
      )

  def calcStandardDeviation(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]): BigDecimal = calcStandardDeviation(numbers, numbers.size, calcMean(numbers))

  implicit class BigIntPlus (numbers: Seq[BigInt]) {
    def sortedNumbers = numbers.sortBy(identity)
    def mean = calcMean(numbers)
    def median = calcMedian(sortedNumbers, numbers.length)
    def mode = calcMode(sortedNumbers)
    def standardDeviation = calcStandardDeviation(numbers, numbers.length, mean)
  }
}
