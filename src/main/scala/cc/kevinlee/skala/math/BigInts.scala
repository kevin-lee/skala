package cc.kevinlee.skala.math

import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object BigInts {
  import collection.immutable.Seq

  /**
   * Returns the square root of a BigInt value.
   * @param number the given BitInt number
   * @return the positive square root of the given number.
   * @throws IllegalArgumentException If the argument is less than zero.
   */
  def sqrt(number: BigInt): BigDecimal = BigDecimals.sqrt(BigDecimal(number))

  def findSqrt(number: BigInt): Option[BigDecimal] = BigDecimals.findSqrt(BigDecimal(number))


  implicit class MathBigInt(number: BigInt) {
    def sqrt(): BigDecimal = BigInts.sqrt(number)
  }

  def mean(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]):BigDecimal = if (numbers.isEmpty) 0 else BigDecimal(numbers.sum) / numbers.size


  def median(sortedNumbers: Seq[BigInt], length: Int):BigDecimal = length match {
    case 0 => 0
    case theLength if isEven(theLength) =>
      val half = theLength / 2
      BigDecimal(sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength => BigDecimal(sortedNumbers(theLength / 2))
  }

  def median(numbers: Seq[BigInt]):BigDecimal = median(numbers.sortBy(identity), numbers.length)

  def mode(sortedNumbers: Seq[BigInt]): BigInt = if (sortedNumbers.isEmpty) 0 else sortedNumbers.groupBy(identity).maxBy(_._2.length)._1


  def standardDeviation(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]], length: Int, mean: BigDecimal): BigDecimal =
    if (length == 0)
      0
    else
      BigDecimals.sqrt(
        numbers.map(BigDecimal(_) - mean)
               .map(x => x * x)
               .sum / length
      )

  def standardDeviation(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]): BigDecimal = standardDeviation(numbers, numbers.size, mean(numbers))
  def stdev(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]): BigDecimal = standardDeviation(numbers)

  implicit class BigIntSeq(numbers: Seq[BigInt]) {
    def sortedNumbers = numbers.sortBy(identity)
    def mean = BigInts.mean(numbers)
    def median = BigInts.median(sortedNumbers, numbers.length)
    def mode = BigInts.mode(sortedNumbers)
    def standardDeviation = BigInts.standardDeviation(numbers, numbers.length, mean)
  }
}
