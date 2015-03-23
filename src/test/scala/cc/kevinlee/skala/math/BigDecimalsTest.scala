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
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(-100.0))" should {
      "return BigDecimal(100.0)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(-100))" should {
      "return BigDecimal(100)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(-1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = -1
        val expected: BigDecimal = 1
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(0))" should {
      "return BigDecimal(0)" in {
        val number: BigDecimal = 0
        val expected: BigDecimal = 0
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = 1
        val expected: BigDecimal = 1
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(100))" should {
      "return BigDecimal(100)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(100.0))" should {
      "return BigDecimal(100.0)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        assert(BigDecimals.abs(number) === expected)
      }
    }
    "abs(BigDecimal(100.0000001))" should {
      "return BigDecimal(100.0000001)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        assert(BigDecimals.abs(number) === expected)
      }
    }
  }
}
