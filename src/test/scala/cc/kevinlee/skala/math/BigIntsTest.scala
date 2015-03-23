package cc.kevinlee.skala.math

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
}
