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
    "sqrt(BigInt(89479223372))" should {
      "return BigDecimal(299130.779713489...) and actual * actual === BigInt(89479223372)" in {
        val number: BigInt = 89479223372L

        val expected = "299130.779713489"
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
    "findSqrt(BigInt(89479223372))" should {
      "return BigDecimal(299130.779713489...) and actual * actual === BigInt(89479223372)" in {
        val number: BigInt = 10
        val expected = Option[BigDecimal](BigInts.sqrt(number))
        val actual = BigInts.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get.round(MathContext.DECIMAL64) === number)
      }
    }
  }
  "BigInts.mean" when {
    val emptyList = List.empty[BigInt]
    s"BigInts.mean($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.mean(emptyList)
        assert(actual === expected)
      }
    }
    s"BigInts.mean(0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.mean(List(0))
        assert(actual === expected)
      }
    }
    s"BigInts.mean(999)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigInts.mean(List(999))
        assert(actual === expected)
      }
    }
    val numbers = List[BigInt](1, 2, 2, 3, 3, 3, 4, 6, 10)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = BigDecimal(numbers.sum) / numbers.length
      s"return $expected" in {
        val actual = BigInts.mean(numbers)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 1, 1, 1, 1)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = 1
      s"return $expected" in {
        val actual = BigInts.mean(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = 2
      s"return $expected" in {
        val actual = BigInts.mean(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](1, 2, 2, 1)
    s"BigInts.mean($numbers)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigInts.mean(numbers4)
        assert(actual === expected)
      }
    }
  }

  "BigInts.median" when {
    val emptyList = Nil
    s"median($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.median(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigInt](0)
    s"median($numbers0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigInts.median(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigInt](999)
    s"median($numbers1)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigInts.median(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigInt](1, 2)
    s"median($numbers2)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigInts.median(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigInt](1, 2, 3)
    s"median($numbers3)" should {
      val expected: BigDecimal = 2
      s"return $expected" in {
        val actual = BigInts.median(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigInt](1, 2, 3, 4)
    s"median($numbers4)" should {
      val expected: BigDecimal = BigDecimal(numbers4(1) + numbers4(2)) / 2
      s"return $expected" in {
        val actual = BigInts.median(numbers4)
        assert(actual === expected)
      }
    }
    val numbers4unordered = List[BigInt](2, 4, 3, 1)
    s"median($numbers4unordered)" should {
      val numbers4ordered = numbers4unordered.sortBy(identity)
      val expected: BigDecimal = BigDecimal(numbers4ordered(1) + numbers4ordered(2)) / 2
      s"return $expected" in {
        val actual = BigInts.median(numbers4unordered)
        assert(actual === expected)
      }
    }
    val numbers5 = List[BigInt](1, 2, 3, 4, 5)
    s"median($numbers5)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigInts.median(numbers5)
        assert(actual === expected)
      }
    }
    val numbers5unordered = List[BigInt](2, 3, 5, 4, 1)
    s"median($numbers5unordered)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigInts.median(numbers5unordered)
        assert(actual === expected)
      }
    }
    val numbers10 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    s"median($numbers10)" should {
      val expected: BigDecimal = BigDecimal(numbers10(4) + numbers10(5)) / 2
      s"return $expected" in {
        val actual = BigInts.median(numbers10)
        assert(actual === expected)
      }
    }
    val numbers11 = List[BigInt](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    s"median($numbers11)" should {
      val expected: BigDecimal = 6
      s"return $expected" in {
        val actual = BigInts.median(numbers11)
        assert(actual === expected)
      }
    }
  }
}
