package io.kevinlee.skala.math

import hedgehog._
import hedgehog.runner._

import scala.reflect.ClassTag
import scala.util.Try
import scala.util.control.NonFatal

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
object BigIntsSpec extends Properties {
  import io.kevinlee.skala.testing.PredefForTesting._

  @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
  implicit class ShouldBeThrown[A](a: => A) {
    def shouldThrow[E <: Throwable](implicit C: ClassTag[E]): Result =
      Try(a).fold(
        {
          case thrown: E =>
            Result.diff(thrown.getMessage, "sqrt can handle only non-negative numbers")(_ contains _)
          case NonFatal(another) =>
            Result.failure.log(s"${C.runtimeClass.getName} should have been thrown but $another was thrown instead.")
        },
        a =>
          Result.failure.log(s"${C.runtimeClass.getName} should have been thrown but it returned $a instead.")
      )

    def shouldThrowWith[E <: Throwable](f: Throwable => Result)(implicit C: ClassTag[E]): Result =
      Try(a).fold(
        {
          case thrown: E =>
            f(thrown)
          case NonFatal(another) =>
            Result.failure.log(s"${C.runtimeClass.getName} should have been thrown but $another was thrown instead.")
        },
        a =>
          Result.failure.log(s"${C.runtimeClass.getName} should have been thrown but it returned $a instead.")
      )
  }

  def testBigIntsSqrtWithInvalid: Result = {
    val number: BigInt = -1
    BigInts.sqrt(number) shouldThrowWith[IllegalArgumentException](ex => Result.diff(ex.getMessage, "sqrt can handle only non-negative numbers")(_ contains _))
  }

  def testBigIntsSqrtWith1: Result = {
    val number: BigInt = 1
    BigInts.sqrt(number) ==== 1
  }

  def testBigIntsSqrtWith0: Result = {
    val number: BigInt = 0
    BigInts.sqrt(number) ==== 0
  }

  def testBigIntsSqrtWith2: Result = {
    val number: BigInt = 2
    BigInts.sqrt(number) isApproximately (BigDecimal("1.414213562373095") +- 0.000000000000001)
  }

  def testBigIntsSqrtWith9: Result = {
    val number: BigInt = 9
    BigInts.sqrt(number) ==== 3
  }

  def testBigIntsSqrtWith10: Result = {
    val number: BigInt = 10
    val expected = BigDecimal("3.162277660168379")
    val actual = BigInts.sqrt(number)
    actual isApproximately (expected +- 0.000000000000001)
    (actual * actual) isApproximately (BigDecimal(number) +- 0.00000000000000000000000000000001)
  }

  def testBigIntsSqrtWith89479223372: Result = {
    val number: BigInt = 89479223372L

    val expected = BigDecimal("299130.779713489")
    val actual = BigInts.sqrt(number)
    actual isApproximately (expected +- 0.000000001)
    (actual * actual) isApproximately (BigDecimal(number) +- 0.0000000000000000000001)
  }

  override def tests: List[Prop] = List(
    example("BigInts.sqrt(BigInt(-1)) should throw java.lang.IllegalArgumentException", testBigIntsSqrtWithInvalid),
    example("BigInts.sqrt(BigInt(1)) should return 1", testBigIntsSqrtWith1),
    example("BigInts.sqrt(BigInt(0)) should return 0", testBigIntsSqrtWith0),
    example("BigInts.sqrt(BigInt(2)) should return approximately 1.414213562373095 +- 0.000000000000001", testBigIntsSqrtWith2),
    example("BigInts.sqrt(BigInt(9)) should return 3", testBigIntsSqrtWith9),
    example("BigInts.sqrt(BigInt(10)) should return BigDecimal(3.162277660168379...) and (actual * actual) is approximately BigInt(10)", testBigIntsSqrtWith10),
    example("sqrt(BigInt(89479223372)) should return BigDecimal(299130.779713489...) and (actual * actual) is approximately BigInt(89479223372)", testBigIntsSqrtWith89479223372),

    example("findSqrt(BigInt(-1)) should return BigDecimal(299130.779713489...) and (actual * actual) is approximately BigInt(89479223372)", testBigIntsSqrtWithMinus1),
  )

  def testBigIntsSqrtWithMinus1: Result = {
    val number: BigInt = -1
    val expected = None
    val actual = BigInts.findSqrt(number)
    actual ==== expected
  }

//  "" when {
//  "BigInts.findSqrt" when {

//    "findSqrt(BigInt(1))" should {
//      "return BigDecimal(1)" in {
//        val number: BigInt = 1
//        val expected = Option(BigDecimal(1))
//        val actual = BigInts.findSqrt(number)
//        assert(actual === expected)
//      }
//    }
//    "findSqrt(BigInt(0))" should {
//      "return BigDecimal(0)" in {
//        val number: BigInt = 0
//        val expected = Option(BigDecimal(0))
//        val actual = BigInts.findSqrt(number)
//        assert(actual === expected)
//      }
//    }
//    "findSqrt(BigInt(2))" should {
//      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
//        val number: BigInt = 2
//        val expected = Option[BigDecimal](BigInts.sqrt(number))
//        val actual = BigInts.findSqrt(number)
//        assert(actual === expected)
//
//        val expectedValue = BigDecimal(number)
//        actual.map(x => x * x) shouldHave expectedValue
//      }
//    }
//    "findSqrt(BigInt(9))" should {
//      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
//        val number: BigInt = 9
//        val expected = Option(BigDecimal(3))
//        val actual = BigInts.findSqrt(number)
//        assert(actual === expected)
//        val expectedValue = BigDecimal(number)
//        actual.map(x => x * x) shouldHave expectedValue
//      }
//    }
//    "findSqrt(BigInt(10))" should {
//      "return BigDecimal(3.162277660168379...) and actual * actual === BigInt(10)" in {
//        val number: BigInt = 10
//        val expected = Option[BigDecimal](BigInts.sqrt(number))
//        val actual = BigInts.findSqrt(number)
//        assert(actual === expected)
//        actual.map(x => x * x) shouldHave approximately (BigDecimal(number) +- 0.00000000000000000000000000000001)
//      }
//    }
//    "findSqrt(BigInt(89479223372))" should {
//      "return BigDecimal(299130.779713489...) and actual * actual === BigInt(89479223372)" in {
//        val number: BigInt = 10
//        val expected = Option[BigDecimal](BigInts.sqrt(number))
//        val actual = BigInts.findSqrt(number)
//        assert(actual === expected)
//        actual.map(x => x * x) shouldHave approximately (BigDecimal(number) +- 0.0000000000000000000001)
//      }
//    }
//  }
//
//  "BigInts.mean" when {
//    val emptyList = List.empty[BigInt]
//    s"BigInts.mean($emptyList)" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = BigInts.mean(emptyList)
//        assert(actual === expected)
//      }
//    }
//    s"BigInts.mean(0)" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = BigInts.mean(List(0))
//        assert(actual === expected)
//      }
//    }
//    s"BigInts.mean(999)" should {
//      val expected: BigDecimal = 999
//      s"return $expected" in {
//        val actual = BigInts.mean(List(999))
//        assert(actual === expected)
//      }
//    }
//    val numbers = List[BigInt](1, 2, 2, 3, 3, 3, 4, 6, 10)
//    s"BigInts.mean($numbers)" should {
//      val expected: BigDecimal = BigDecimal(numbers.sum) / numbers.length
//      s"return $expected" in {
//        val actual = BigInts.mean(numbers)
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 1, 1, 1, 1)
//    s"BigInts.mean($numbers)" should {
//      val expected: BigDecimal = 1
//      s"return $expected" in {
//        val actual = BigInts.mean(numbers2)
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"BigInts.mean($numbers)" should {
//      val expected: BigDecimal = 2
//      s"return $expected" in {
//        val actual = BigInts.mean(numbers3)
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](1, 2, 2, 1)
//    s"BigInts.mean($numbers)" should {
//      val expected: BigDecimal = 1.5
//      s"return $expected" in {
//        val actual = BigInts.mean(numbers4)
//        assert(actual === expected)
//      }
//    }
//  }
//  "implicit BigIntSeq.mean" when {
//    import BigInts.BigIntSeq
//    val emptyList = List.empty[BigInt]
//    s"$emptyList.mean" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = emptyList.mean
//        assert(actual === expected)
//      }
//    }
//    s"List(0).mean" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = List[BigInt](0).mean
//        assert(actual === expected)
//      }
//    }
//    s"List(999).mean" should {
//      val expected: BigDecimal = 999
//      s"return $expected" in {
//        val actual = List[BigInt](999).mean
//        assert(actual === expected)
//      }
//    }
//    val numbers = List[BigInt](1, 2, 2, 3, 3, 3, 4, 6, 10)
//    s"$numbers.mean" should {
//      val expected: BigDecimal = BigDecimal(numbers.sum) / numbers.length
//      s"return $expected" in {
//        val actual = numbers.mean
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 1, 1, 1, 1)
//    s"$numbers.mean" should {
//      val expected: BigDecimal = 1
//      s"return $expected" in {
//        val actual = numbers2.mean
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"$numbers.mean" should {
//      val expected: BigDecimal = 2
//      s"return $expected" in {
//        val actual = numbers3.mean
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](1, 2, 2, 1)
//    s"$numbers.mean" should {
//      val expected: BigDecimal = 1.5
//      s"return $expected" in {
//        val actual = numbers4.mean
//        assert(actual === expected)
//      }
//    }
//  }
//
//  "BigInts.median" when {
//    val emptyList = Nil
//    s"median($emptyList)" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = BigInts.median(emptyList)
//        assert(actual === expected)
//      }
//    }
//    val numbers0 = List[BigInt](0)
//    s"median($numbers0)" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = BigInts.median(numbers0)
//        assert(actual === expected)
//      }
//    }
//    val numbers1 = List[BigInt](999)
//    s"median($numbers1)" should {
//      val expected: BigDecimal = 999
//      s"return $expected" in {
//        val actual = BigInts.median(numbers1)
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 2)
//    s"median($numbers2)" should {
//      val expected: BigDecimal = 1.5
//      s"return $expected" in {
//        val actual = BigInts.median(numbers2)
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"median($numbers3)" should {
//      val expected: BigDecimal = 2
//      s"return $expected" in {
//        val actual = BigInts.median(numbers3)
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](1, 2, 3, 4)
//    s"median($numbers4)" should {
//      val expected: BigDecimal = BigDecimal(numbers4(1) + numbers4(2)) / 2
//      s"return $expected" in {
//        val actual = BigInts.median(numbers4)
//        assert(actual === expected)
//      }
//    }
//    val numbers4unordered = List[BigInt](2, 4, 3, 1)
//    s"median($numbers4unordered)" should {
//      val numbers4ordered = numbers4unordered.sorted
//      val expected: BigDecimal = BigDecimal(numbers4ordered(1) + numbers4ordered(2)) / 2
//      s"return $expected" in {
//        val actual = BigInts.median(numbers4unordered)
//        assert(actual === expected)
//      }
//    }
//    val numbers5 = List[BigInt](1, 2, 3, 4, 5)
//    s"median($numbers5)" should {
//      val expected: BigDecimal = 3
//      s"return $expected" in {
//        val actual = BigInts.median(numbers5)
//        assert(actual === expected)
//      }
//    }
//    val numbers5unordered = List[BigInt](2, 3, 5, 4, 1)
//    s"median($numbers5unordered)" should {
//      val expected: BigDecimal = 3
//      s"return $expected" in {
//        val actual = BigInts.median(numbers5unordered)
//        assert(actual === expected)
//      }
//    }
//    val numbers10 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//    s"median($numbers10)" should {
//      val expected: BigDecimal = BigDecimal(numbers10(4) + numbers10(5)) / 2
//      s"return $expected" in {
//        val actual = BigInts.median(numbers10)
//        assert(actual === expected)
//      }
//    }
//    val numbers11 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
//    s"median($numbers11)" should {
//      val expected: BigDecimal = 6
//      s"return $expected" in {
//        val actual = BigInts.median(numbers11)
//        assert(actual === expected)
//      }
//    }
//  }
//
//  "implicit BigIntSeq.median" when {
//    import BigInts.BigIntSeq
//
//    val emptyList = Nil
//    s"$emptyList.median" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = emptyList.median
//        assert(actual === expected)
//      }
//    }
//    val numbers0 = List[BigInt](0)
//    s"$numbers0.median" should {
//      val expected: BigDecimal = 0
//      s"return $expected" in {
//        val actual = numbers0.median
//        assert(actual === expected)
//      }
//    }
//    val numbers1 = List[BigInt](999)
//    s"$numbers1.median" should {
//      val expected: BigDecimal = 999
//      s"return $expected" in {
//        val actual = numbers1.median
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 2)
//    s"$numbers2.median" should {
//      val expected: BigDecimal = 1.5
//      s"return $expected" in {
//        val actual = numbers2.median
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"$numbers3.median" should {
//      val expected: BigDecimal = 2
//      s"return $expected" in {
//        val actual = numbers3.median
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](1, 2, 3, 4)
//    s"$numbers4.median" should {
//      val expected: BigDecimal = BigDecimal(numbers4(1) + numbers4(2)) / 2
//      s"return $expected" in {
//        val actual = numbers4.median
//        assert(actual === expected)
//      }
//    }
//    val numbers4unordered = List[BigInt](2, 4, 3, 1)
//    s"$numbers4unordered.median" should {
//      val numbers4ordered = numbers4unordered.sorted
//      val expected: BigDecimal = BigDecimal(numbers4ordered(1) + numbers4ordered(2)) / 2
//      s"return $expected" in {
//        val actual = numbers4unordered.median
//        assert(actual === expected)
//      }
//    }
//    val numbers5 = List[BigInt](1, 2, 3, 4, 5)
//    s"$numbers5.median" should {
//      val expected: BigDecimal = 3
//      s"return $expected" in {
//        val actual = numbers5.median
//        assert(actual === expected)
//      }
//    }
//    val numbers5unordered = List[BigInt](2, 3, 5, 4, 1)
//    s"$numbers5unordered.median" should {
//      val expected: BigDecimal = 3
//      s"return $expected" in {
//        val actual = numbers5unordered.median
//        assert(actual === expected)
//      }
//    }
//    val numbers10 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//    s"$numbers10.median" should {
//      val expected: BigDecimal = BigDecimal(numbers10(4) + numbers10(5)) / 2
//      s"return $expected" in {
//        val actual = numbers10.median
//        assert(actual === expected)
//      }
//    }
//    val numbers11 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
//    s"$numbers11.median" should {
//      val expected: BigDecimal = 6
//      s"return $expected" in {
//        val actual = numbers11.median
//        assert(actual === expected)
//      }
//    }
//  }
//
//
//  "BigInts.mode" when {
//    val emptyList = List.empty[BigInt]
//    s"mode($emptyList)" should {
//      val expected = List.empty[BigInt]
//      s"return $expected" in {
//        val actual = BigInts.mode(emptyList)
//        assert(actual === expected)
//      }
//    }
//    val numbers0 = List[BigInt](0)
//    s"mode($numbers0)" should {
//      val expected = List[BigInt](0)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers0)
//        assert(actual === expected)
//      }
//    }
//    val numbers1 = List[BigInt](999)
//    s"mode($numbers1)" should {
//      val expected = List[BigInt](999)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers1)
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 2)
//    s"mode($numbers2)" should {
//      val expected = List[BigInt](1, 2)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers2)
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"mode($numbers3)" should {
//      val expected = List[BigInt](1, 2, 3)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers3)
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](3, 7, 5, 13, 20, 23, 39, 23, 40, 23, 14, 12, 56, 23, 29)
//    s"mode($numbers4)" should {
//      val expected = List[BigInt](23)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers4)
//        assert(actual === expected)
//      }
//    }
//    val numbers5 = List[BigInt](1, 3, 3, 3, 4, 4, 6, 6, 6, 9)
//    s"mode($numbers5)" should {
//      val expected = List[BigInt](3, 6)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers5)
//        assert(actual === expected)
//      }
//    }
//    val numbers6 = List[BigInt](1, 1, 2, 3, 3, 3, 3, 5, 5, 7, 7, 7, 7, 100, 101, 101, 101, 101, 8)
//    s"mode($numbers6)" should {
//      val expected = List[BigInt](3, 7, 101)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers6)
//        assert(actual === expected)
//      }
//    }
//    val bigIntOfLongMax = BigInt(Long.MaxValue)
//    val longMaxX2 = bigIntOfLongMax * 2
//    val longMaxX3 = bigIntOfLongMax * 3
//    val numbers7 = List[BigInt](1, 1, 2, longMaxX3, longMaxX3, longMaxX3, longMaxX3, longMaxX3, 5, 5, 7, 7, 7, 7, longMaxX2, longMaxX2, longMaxX2, longMaxX2, longMaxX2, 8)
//    s"mode($numbers7)" should {
//      val expected = List[BigInt](longMaxX2, longMaxX3)
//      s"return $expected" in {
//        val actual = BigInts.mode(numbers7)
//        assert(actual === expected)
//      }
//    }
//  }
//
//  "implict BigIntSeq.mode" when {
//    import BigInts.BigIntSeq
//    val emptyList = List.empty[BigInt]
//    s"$emptyList.mode" should {
//      val expected = List.empty[BigInt]
//      s"return $expected" in {
//        val actual = emptyList.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers0 = List[BigInt](0)
//    s"$numbers0.mode" should {
//      val expected = List[BigInt](0)
//      s"return $expected" in {
//        val actual = numbers0.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers1 = List[BigInt](999)
//    s"$numbers1.mode" should {
//      val expected = List[BigInt](999)
//      s"return $expected" in {
//        val actual = numbers1.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 2)
//    s"$numbers2.mode" should {
//      val expected = List[BigInt](1, 2)
//      s"return $expected" in {
//        val actual = numbers2.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"$numbers3.mode" should {
//      val expected = List[BigInt](1, 2, 3)
//      s"return $expected" in {
//        val actual = numbers3.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](3, 7, 5, 13, 20, 23, 39, 23, 40, 23, 14, 12, 56, 23, 29)
//    s"$numbers4.mode" should {
//      val expected = List[BigInt](23)
//      s"return $expected" in {
//        val actual = numbers4.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers5 = List[BigInt](1, 3, 3, 3, 4, 4, 6, 6, 6, 9)
//    s"$numbers5.mode" should {
//      val expected = List[BigInt](3, 6)
//      s"return $expected" in {
//        val actual = numbers5.mode
//        assert(actual === expected)
//      }
//    }
//    val numbers6 = List[BigInt](1, 1, 2, 3, 3, 3, 3, 5, 5, 7, 7, 7, 7, 100, 101, 101, 101, 101, 8)
//    s"$numbers6.mode" should {
//      val expected = List[BigInt](3, 7, 101)
//      s"return $expected" in {
//        val actual = numbers6.mode
//        assert(actual === expected)
//      }
//    }
//    val bigIntOfLongMax = BigInt(Long.MaxValue)
//    val longMaxX2 = bigIntOfLongMax * 2
//    val longMaxX3 = bigIntOfLongMax * 3
//    val numbers7 = List[BigInt](1, 1, 2, longMaxX3, longMaxX3, longMaxX3, longMaxX3, longMaxX3, 5, 5, 7, 7, 7, 7, longMaxX2, longMaxX2, longMaxX2, longMaxX2, longMaxX2, 8)
//    s"$numbers7.mode" should {
//      val expected = List[BigInt](longMaxX2, longMaxX3)
//      s"return $expected" in {
//        val actual = numbers7.mode
//        assert(actual === expected)
//      }
//    }
//  }
//
//  "BigInts.stdev" when {
//    val emptyList = List.empty[BigInt]
//    s"stdev($emptyList)" should {
//      val expected = BigDecimal(0)
//      s"return $expected" in {
//        val actual = BigInts.stdev(emptyList)
//        assert(actual === expected)
//      }
//    }
//    val numbers0 = List[BigInt](0)
//    s"stdev($numbers0)" should {
//      val expected = BigDecimal(0)
//      s"return $expected" in {
//        val actual = BigInts.stdev(numbers0)
//        assert(actual === expected)
//      }
//    }
//    val numbers1 = List[BigInt](999)
//    s"stdev($numbers1)" should {
//      val expected = BigDecimal(0)
//      s"return $expected" in {
//        val actual = BigInts.stdev(numbers1)
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 2)
//    s"stdev($numbers2)" should {
//      val expected = BigDecimals.sqrt(BigDecimal("0.25"))
//      s"return $expected" in {
//        val actual = BigInts.stdev(numbers2)
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"stdev($numbers3)" should {
//      val expected = BigDecimals.sqrt(BigDecimal(2) / 3)
//      s"return $expected" in {
//        val actual = BigInts.stdev(numbers3)
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](9, 2, 5, 4, 12, 7, 8, 11, 9, 3, 7, 4, 12, 5, 4, 10, 9, 6, 9, 4)
//    s"stdev($numbers4)" should {
//      val expected = BigDecimal("2.9832")
//      s"return $expected" in {
//        val actual = BigInts.stdev(numbers4)
//        assert(actual === (expected +- 0.0001))
//      }
//    }
//  }
//
//  "implicit BigIntSeq.stdev" when {
//    import BigInts.BigIntSeq
//
//    val emptyList = List.empty[BigInt]
//    s"$emptyList.stdev" should {
//      val expected = BigDecimal(0)
//      s"return $expected" in {
//        val actual = emptyList.stdev
//        assert(actual === expected)
//      }
//    }
//    val numbers0 = List[BigInt](0)
//    s"$numbers0.stdev" should {
//      val expected = BigDecimal(0)
//      s"return $expected" in {
//        val actual = numbers0.stdev
//        assert(actual === expected)
//      }
//    }
//    val numbers1 = List[BigInt](999)
//    s"$numbers1.stdev" should {
//      val expected = BigDecimal(0)
//      s"return $expected" in {
//        val actual = numbers1.stdev
//        assert(actual === expected)
//      }
//    }
//    val numbers2 = List[BigInt](1, 2)
//    s"$numbers2.stdev" should {
//      val expected = BigDecimals.sqrt(BigDecimal("0.25"))
//      s"return $expected" in {
//        val actual = numbers2.stdev
//        assert(actual === expected)
//      }
//    }
//    val numbers3 = List[BigInt](1, 2, 3)
//    s"$numbers3.stdev" should {
//      val expected = BigDecimals.sqrt(BigDecimal(2) / 3)
//      s"return $expected" in {
//        val actual = numbers3.stdev
//        assert(actual === expected)
//      }
//    }
//    val numbers4 = List[BigInt](9, 2, 5, 4, 12, 7, 8, 11, 9, 3, 7, 4, 12, 5, 4, 10, 9, 6, 9, 4)
//    s"$numbers4.stdev" should {
//      val expected = BigDecimal("2.9832")
//      s"return $expected" in {
//        val actual = numbers4.stdev
//        assert(actual === (expected +- 0.0001))
//      }
//    }
//  }
//
//
//  "BigInts.toOrdinal" when {
//    "toOrdinal(1)" should {
//      val number: BigInt = 1
//      val expected = s"${number}st"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(2)" should {
//      val number = 2
//      val expected = s"${number}nd"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(3)" should {
//      val number = 3
//      val expected = s"${number}rd"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(4)" should {
//      val number = 4
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(5)" should {
//      val number = 5
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(10)" should {
//      val number = 10
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(11)" should {
//      val number = 11
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(12)" should {
//      val number = 12
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(13)" should {
//      val number = 13
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(14)" should {
//      val number = 14
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(20)" should {
//      val number = 20
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(21)" should {
//      val number = 21
//      val expected = s"${number}st"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(22)" should {
//      val number = 22
//      val expected = s"${number}nd"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(23)" should {
//      val number = 23
//      val expected = s"${number}rd"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "toOrdinal(24)" should {
//      val number = 24
//      val expected = s"${number}th"
//      s"""return "$expected"""" in {
//        val actual = BigInts.toOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//
//  }
//
//  "BigInts.findOrdinal" when {
//
//    "findOrdinal(-1)" should {
//      val number = -1
//      val expected = None
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(0)" should {
//      val number = 0
//      val expected = None
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(1)" should {
//      val number = 1
//      val expected = Some(s"${number}st")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(2)" should {
//      val number = 2
//      val expected = Some(s"${number}nd")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(3)" should {
//      val number = 3
//      val expected = Some(s"${number}rd")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(4)" should {
//      val number = 4
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(5)" should {
//      val number = 5
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(10)" should {
//      val number = 10
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(11)" should {
//      val number = 11
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(12)" should {
//      val number = 12
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(13)" should {
//      val number = 13
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(14)" should {
//      val number = 14
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(20)" should {
//      val number = 20
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(21)" should {
//      val number = 21
//      val expected = Some(s"${number}st")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(22)" should {
//      val number = 22
//      val expected = Some(s"${number}nd")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(23)" should {
//      val number = 23
//      val expected = Some(s"${number}rd")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//    "findOrdinal(24)" should {
//      val number = 24
//      val expected = Some(s"${number}th")
//      s"return $expected" in {
//        val actual = BigInts.findOrdinal(number)
//        assert(actual === expected)
//      }
//    }
//
//  }

}
