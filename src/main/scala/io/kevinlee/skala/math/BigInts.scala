package io.kevinlee.skala.math

import scala.collection.TraversableLike

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
object BigInts {

  /**
   * Returns the square root of a BigInt value.
   * @param number the given BitInt number
   * @return the positive square root of the given number.
   */
  @throws[IllegalArgumentException]("If the argument is less than zero.")
  def sqrt(number: BigInt): BigDecimal = BigDecimals.sqrt(BigDecimal(number))

  def findSqrt(number: BigInt): Option[BigDecimal] = BigDecimals.findSqrt(BigDecimal(number))

  implicit class MathBigInt(val number: BigInt) extends AnyVal {
    def sqrt(): BigDecimal = BigInts.sqrt(number)
    def toOrdinal: String = BigInts.toOrdinal(number)
    def findOrdinal: Option[String] = BigInts.findOrdinal(number)
  }

  def mean(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]): BigDecimal = if (numbers.isEmpty) 0 else BigDecimal(numbers.sum) / numbers.size


  /**
   * Returns Median value of the given Seq of BigInt.
   * <pre>
   * WARNING!!! The given Seq must be sorted.
   * </pre>
   *
   * <p>
   *   <ul>
   *     <li>
   *       If the number of elements (n) is 0, it returns 0.
   *     </li>
   *   </ul>
   *   <ul>
   *     <li>
   *       If the number of elements (n) is odd, median(sorted Seq of BigInt) = The value of (n / 2)th item.
   *     </li>
   *   </ul>
   *   <ul>
   *     <li>
   *       If the number of elements (n) is even, median(sorted Seq of BigInt) = The value of [(n / 2 - 1)th item + (n / 2)th item term ] / 2
   *     </li>
   *   </ul>
   * </p>
   *
   * @param sortedNumbers the given Seq of BigInt. This must be sorted.
   * @param length The length of the given Seq
   * @return The median value of the given Seq.
   */
  def median(sortedNumbers: Seq[BigInt], length: Int): BigDecimal = length match {
    case 0 => 0
    case theLength if CommonMath.isEven(theLength) =>
      val half = theLength / 2
      BigDecimal(sortedNumbers(half - 1) + sortedNumbers(half)) / 2
    case theLength =>
      BigDecimal(sortedNumbers(theLength / 2))
  }

  /**
   * Returns Median value of the given Seq of BigInt.
   * The given Seq will be sorted so it doesn't have to be soreted when it's passed as a parameter.
   * If the Seq is already sorted, better use `BigInts.median(sortedNumbers: Seq[BigInt], length: Int)`
   *
   * <p>
   *   <ul>
   *     <li>
   *       If the number of elements (n) is 0, it returns 0.
   *     </li>
   *   </ul>
   *   <ul>
   *     <li>
   *       If the number of elements (n) is odd, median(sorted Seq of BigInt) = The value of (n / 2)th item.
   *     </li>
   *   </ul>
   *   <ul>
   *     <li>
   *       If the number of elements (n) is even, median(sorted Seq of BigInt) = The value of [(n / 2 - 1)th item + (n / 2)th item term ] / 2
   *     </li>
   *   </ul>
   * </p>
   *
   * @param numbers the given Seq of BigInt. This does not have to be sorted and it will be sorted.
   * @return The median value of the given Seq.
   */
  def median(numbers: Seq[BigInt]): BigDecimal = median(numbers.sorted, numbers.length)

  def mode(numbers: Seq[BigInt]): Seq[BigInt] = CommonMath.mode(numbers)

  def stdev(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]], length: Int, mean: BigDecimal): BigDecimal =
    if (length == 0)
      0
    else
      BigDecimals.sqrt(
        numbers.map(BigDecimal(_) - mean)
               .map(x => x * x)
               .sum / length
      )

  /**
   * Computes the Standard Deviation of the given BigInt numbers.
   *
   * <pre>
   * variance = ((x1 - mean)&#94;2 + (x2 - mean)&#94;2  + ... + (xn - mean)&#94;2) / n
   * </pre>
   * <pre>
   * standard deviation = √variance
   * </pre>
   *
   * @param numbers the given BigInt numbers
   * @return the Standard Deviation of the given BigInt numbers
   */
  def stdev(numbers: TraversableLike[BigInt, TraversableLike[BigInt, _]]): BigDecimal = stdev(numbers, numbers.size, mean(numbers))

  implicit class BigIntSeq(val numbers: Seq[BigInt]) extends AnyVal {
    def mean: BigDecimal = BigInts.mean(numbers)
    def median: BigDecimal = BigInts.median(numbers.sorted, numbers.length)
    def mode: Seq[BigInt] = BigInts.mode(numbers.sorted)
    def stdev: BigDecimal = BigInts.stdev(numbers, numbers.length, mean)
  }

  private final val bigInts_11_12_13 =Set(BigInt(11), BigInt(12), BigInt(13))
  def toOrdinal(number: BigInt): String =
    if (bigInts_11_12_13 contains number)
      s"${number}th"
    else if (number > 0)
      (number % 10).toInt match {
        case 1 => s"${number}st"
        case 2 => s"${number}nd"
        case 3 => s"${number}rd"
        case _ => s"${number}th"
      }
    else ""

  def findOrdinal(number: BigInt): Option[String] = Option(number).map(toOrdinal).filterNot(_.isEmpty)
}
