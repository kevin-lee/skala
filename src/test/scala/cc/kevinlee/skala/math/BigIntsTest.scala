package cc.kevinlee.skala.math

import java.math.MathContext

import org.scalatest.WordSpec

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
class BigIntsTest extends WordSpec {

  "BigInts.sqrt" when {
    "sqrt(BigInt(-1))" should {
      "throw java.lang.IllegalArgumentException" in {
        val number: BigInt = -1
        val thrown = intercept[IllegalArgumentException] {
          BigInts.sqrt(number)
        }
        assert(thrown.getMessage contains "sqrt can handle only non-negative numbers")
      }
    }
    "sqrt(BigInt(1))" should {
      "return BigDecimal(1)" in {
        val number: BigInt = 1
        val expected: BigDecimal = 1
        val actual = BigInts.sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(BigInt(0))" should {
      "return BigDecimal(0)" in {
        val number: BigInt = 0
        val expected: BigDecimal = 0
        val actual = BigInts.sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(BigInt(2))" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
        val number: BigInt = 2
        val expected = "1.414213562373095"
        val actual = BigInts.sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual) === number)
      }
    }
    "sqrt(BigInt(9))" should {
      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
        val number: BigInt = 9
        val expected: BigDecimal = 3
        val actual = BigInts.sqrt(number)
        assert(actual === expected)
        assert((actual * actual) === number)
      }
    }
    "sqrt(BigInt(10))" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === BigInt(10)" in {
        val number: BigInt = 10
        val expected = "3.162277660168379"
        val actual = BigInts.sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual).round(MathContext.DECIMAL64) === number)
      }
    }
  }
  "BigInts.findSqrt" when {
    "findSqrt(BigInt(-1))" should {
      "return None" in {
        val number: BigInt = -1
        val expected = None
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigInt(1))" should {
      "return BigDecimal(1)" in {
        val number: BigInt = 1
        val expected = Option[BigDecimal](1)
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigInt(0))" should {
      "return BigDecimal(0)" in {
        val number: BigInt = 0
        val expected = Option[BigDecimal](0)
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigInt(2))" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
        val number: BigInt = 2
        val expected = Option[BigDecimal](BigInts.sqrt(number))
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === number)
      }
    }
    "findSqrt(BigInt(9))" should {
      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
        val number: BigInt = 9
        val expected = Option[BigDecimal](3)
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === number)
      }
    }
    "findSqrt(BigInt(10))" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === BigInt(10)" in {
        val number: BigInt = 10
        val expected = Option[BigDecimal](BigInts.sqrt(number))
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get.round(MathContext.DECIMAL64) === number)
      }
    }
  }

  "BigInts.calcMedian" when {
    val emptyList = Nil
    s"calcMedian($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.calcMedian(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigInt](0)
    s"calcMedian($numbers0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigInt](999)
    s"calcMedian($numbers1)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 2)
    s"calcMedian($numbers2)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"calcMedian($numbers3)" should {
      val expected: BigDecimal = 2
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](1, 2, 3, 4)
    s"calcMedian($numbers4)" should {
      val expected: BigDecimal = BigDecimal(numbers4(1) + numbers4(2)) / 2
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers4)
        assert(actual === expected)
      }
    }
    val numbers5 = List[BigInt](1, 2, 3, 4, 5)
    s"calcMedian($numbers5)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers5)
        assert(actual === expected)
      }
    }
    val numbers10 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    s"calcMedian($numbers10)" should {
      val expected: BigDecimal = BigDecimal((numbers10(4)) + numbers10(5)) / 2
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers10)
        assert(actual === expected)
      }
    }
    val numbers11 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    s"calcMedian($numbers11)" should {
      val expected: BigDecimal = 6
      s"return $expected" in {
        val actual = BigInts.calcMedian(numbers11)
        assert(actual === expected)
      }
    }
  }
}
