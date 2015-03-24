package cc.kevinlee.skala.math

import org.scalatest.WordSpec

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
class BigDecimalsTest extends WordSpec {

  "BigDecimals.abs" when {
    "abs(BigDecimal(-100.0000001))" should {
      "return BigDecimal(100.0000001)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(-100.0))" should {
      "return BigDecimal(100.0)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(-100))" should {
      "return BigDecimal(100)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(-1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = -1
        val expected: BigDecimal = 1
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(0))" should {
      "return BigDecimal(0)" in {
        val number: BigDecimal = 0
        val expected: BigDecimal = 0
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = 1
        val expected: BigDecimal = 1
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(100))" should {
      "return BigDecimal(100)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(100.0))" should {
      "return BigDecimal(100.0)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
    "abs(BigDecimal(100.0000001))" should {
      "return BigDecimal(100.0000001)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        val actual = BigDecimals.abs(number)
        assert(actual === expected)
      }
    }
  }
  "BigDecimals.sqrt" when {
    "sqrt(BigDecimal(-1))" should {
      "throw java.lang.NumberFormatException" in {
        val number: BigDecimal = -1
        val thrown = intercept[IllegalArgumentException] {
          BigDecimals.sqrt(number)
        }
        assert(thrown.getMessage contains "sqrt can handle only non-negative numbers")
      }
    }
    "sqrt(BigDecimal(0))" should {
      "return BigDecimal(0)" in {
        val number: BigDecimal = 0
        val expected: BigDecimal = 0
        assert(BigDecimals.sqrt(number) === expected)
      }
    }
    "sqrt(BigDecimal(1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = 1
        val expected: BigDecimal = 1
        assert(BigDecimals.sqrt(number) === expected)
      }
    }
    "sqrt(BigDecimal(2))" should {
      "return BigDecimal(1.414213562373095...)" in {
        val number: BigDecimal = 2
        val expected = "1.414213562373095"
        assert(BigDecimals.sqrt(number).toString startsWith expected)
      }
    }
    "sqrt(BigDecimal(9))" should {
      "return BigDecimal(3)" in {
        val number: BigDecimal = 9
        val expected: BigDecimal = 3
        assert(BigDecimals.sqrt(number) === expected)
      }
    }
    "sqrt(BigDecimal(10))" should {
      "return BigDecimal(3.162277660168379...)" in {
        val number: BigDecimal = 10
        val expected = "3.162277660168379"
        assert(BigDecimals.sqrt(number).toString startsWith expected)
      }
    }
  }
  "BigDecimals.findSqrt" when {
    "findSqrt(BigDecimal(-1))" should {
      "return None" in {
        val number: BigDecimal = -1
        val expected = None
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigDecimal(1))" should {
      "return Some(BigDecimal(1))" in {
        val number: BigDecimal = 1
        val expected = Option[BigDecimal](1)
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigDecimal(0))" should {
      "return Some(BigDecimal(0))" in {
        val number: BigDecimal = 0
        val expected = Option[BigDecimal](0)
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigDecimal(2))" should {
      "return Some(BigDecimal(1.414213562373095...))" in {
        val number: BigDecimal = 2
        val expected = Option[BigDecimal](BigDecimals.sqrt(number))
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigDecimal(9))" should {
      "return Some(BigDecimal(3))" in {
        val number: BigDecimal = 9
        val expected = Option[BigDecimal](3)
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
      }
    }
    "findSqrt(BigDecimal(10))" should {
      "return Some(BigDecimal(3.162277660168379...))" in {
        val number: BigDecimal = 10
        val expected = Option[BigDecimal](BigDecimals.sqrt(number))
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
      }
    }
  }
}
