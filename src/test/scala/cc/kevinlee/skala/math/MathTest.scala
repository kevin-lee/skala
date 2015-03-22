package cc.kevinlee.skala.math

import org.scalatest.WordSpec

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
class MathTest extends WordSpec {

  "Math.absBigInt" when {
    "absBigInt(BigInt(-100))" should {
      "return BigInt(100)" in {
        val number: BigInt = -100
        val expected: BigInt = 100
        assert(Math.absBigInt(number) === expected)
      }
    }
    "absBigInt(BigInt(-1))" should {
      "return BigInt(1)" in {
        val number: BigInt = -1
        val expected: BigInt = 1
        assert(Math.absBigInt(number) === expected)
      }
    }
    "absBigInt(BigInt(0))" should {
      "return BigInt(0)" in {
        val number: BigInt = 0
        val expected: BigInt = 0
        assert(Math.absBigInt(number) === expected)
      }
    }
    "absBigInt(BigInt(1))" should {
      "return BigInt(1)" in {
        val number: BigInt = 1
        val expected: BigInt = 1
        assert(Math.absBigInt(number) === expected)
      }
    }
    "absBigInt(BigInt(100))" should {
      "return BigInt(100)" in {
        val number: BigInt = 100
        val expected: BigInt = 100
        assert(Math.absBigInt(number) === expected)
      }
    }
  }
  "Math.abs" when {
    "abs(BigDecimal(-100.0000001))" should {
      "return BigDecimal(100.0000001)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(-100.0))" should {
      "return BigDecimal(100.0)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(-100))" should {
      "return BigDecimal(100)" in {
        val number: BigDecimal = -100
        val expected: BigDecimal = 100
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(-1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = -1
        val expected: BigDecimal = 1
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(0))" should {
      "return BigDecimal(0)" in {
        val number: BigDecimal = 0
        val expected: BigDecimal = 0
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = 1
        val expected: BigDecimal = 1
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(100))" should {
      "return BigDecimal(100)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(100.0))" should {
      "return BigDecimal(100.0)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        assert(Math.abs(number) === expected)
      }
    }
    "abs(BigDecimal(100.0000001))" should {
      "return BigDecimal(100.0000001)" in {
        val number: BigDecimal = 100
        val expected: BigDecimal = 100
        assert(Math.abs(number) === expected)
      }
    }
  }
}
