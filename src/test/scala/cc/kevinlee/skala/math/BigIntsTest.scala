package cc.kevinlee.skala.math

import java.math.MathContext

import org.scalatest.WordSpec

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
class BigIntsTest extends WordSpec {

  "BigInts.abs" when {
    "abs(BigInt(-100))" should {
      "return BigInt(100)" in {
        val number: BigInt = -100
        val expected: BigInt = 100
        assert(BigInts.abs(number) === expected)
      }
    }
    "abs(BigInt(-1))" should {
      "return BigInt(1)" in {
        val number: BigInt = -1
        val expected: BigInt = 1
        assert(BigInts.abs(number) === expected)
      }
    }
    "abs(BigInt(0))" should {
      "return BigInt(0)" in {
        val number: BigInt = 0
        val expected: BigInt = 0
        assert(BigInts.abs(number) === expected)
      }
    }
    "abs(BigInt(1))" should {
      "return BigInt(1)" in {
        val number: BigInt = 1
        val expected: BigInt = 1
        assert(BigInts.abs(number) === expected)
      }
    }
    "abs(BigInt(100))" should {
      "return BigInt(100)" in {
        val number: BigInt = 100
        val expected: BigInt = 100
        assert(BigInts.abs(number) === expected)
      }
    }
  }
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
}
