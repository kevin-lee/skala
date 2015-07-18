package cc.kevinlee.skala.math

import java.math.MathContext

import org.scalatest.WordSpec

/**
 * @author Lee, SeongHyun (Kevin)
 * @since 2015-03-22
 */
class BigDecimalsSpec extends WordSpec {

  "BigDecimals.sqrt" when {
    "sqrt(BigDecimal(-1))" should {
      "throw java.lang.IllegalArgumentException" in {
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
        val actual = BigDecimals.sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(BigDecimal(1))" should {
      "return BigDecimal(1)" in {
        val number: BigDecimal = 1
        val expected: BigDecimal = 1
        val actual = BigDecimals.sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(BigDecimal(2))" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigDecimal(2)" in {
        val number: BigDecimal = 2
        val expected = "1.414213562373095"
        val actual = BigDecimals.sqrt(number)
        assert(actual.toString startsWith expected)
        assert(actual * actual === number)
      }
    }
    "sqrt(BigDecimal(9))" should {
      "return BigDecimal(3) and actual * actual === BigDecimal(9)" in {
        val number: BigDecimal = 9
        val expected: BigDecimal = 3
        val actual = BigDecimals.sqrt(number)
        assert(actual === expected)
        assert(actual * actual === number)
      }
    }
    "sqrt(BigDecimal(10))" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === BigDecimal(10)" in {
        val number: BigDecimal = 10
        val expected = "3.162277660168379"
        val actual = BigDecimals.sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual).round(MathContext.DECIMAL64) === number)
      }
    }
    "sqrt(BigDecimal(6E-10))" should {
      "return BigDecimal(0.00002449489742783178...) and actual * actual === BigDecimal(6E-10)" in {
        val number: BigDecimal = 6E-10
        val expected = "0.00002449489742783178"
        val actual = BigDecimals.sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual).round(MathContext.DECIMAL64) === number)
      }
    }
    "sqrt(BigDecimal(894792233.72423))" should {
      "return BigDecimal(29913.07797141962...) and actual * actual === BigDecimal(894792233.72423)" in {
        val number: BigDecimal = BigDecimal("894792233.72423")
        val expected = "29913.07797141962"
        val actual = BigDecimals.sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual).round(MathContext.DECIMAL64) === number)
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
      "return Some(BigDecimal(1.414213562373095...)) and actual * actual === BigDecimal(2)" in {
        val number: BigDecimal = 2
        val expected = Option[BigDecimal](BigDecimals.sqrt(number))
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === number)
      }
    }
    "findSqrt(BigDecimal(9))" should {
      "return Some(BigDecimal(3)) and actual * actual === BigDecimal(9)" in {
        val number: BigDecimal = 9
        val expected = Option[BigDecimal](3)
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get === number)
      }
    }
    "findSqrt(BigDecimal(10))" should {
      "return Some(BigDecimal(3.162277660168379...)) and actual * actual === BigDecimal(10)" in {
        val number: BigDecimal = 10
        val expected = Option[BigDecimal](BigDecimals.sqrt(number))
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get.round(MathContext.DECIMAL64) === number)
      }
    }
    "findSqrt(BigDecimal(6E-10))" should {
      "return Some(BigDecimal(0.00002449489742783178...)) and actual * actual === BigDecimal(6E-10)" in {
        val number: BigDecimal = 6E-10
        val expected = Option[BigDecimal](BigDecimals.sqrt(number))
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get.round(MathContext.DECIMAL64) === number)
      }
    }
    "findSqrt(BigDecimal(894792233.72423))" should {
      "return Some(BigDecimal(29913.07797141962...)) and actual * actual === BigDecimal(894792233.72423)" in {
        val number: BigDecimal = BigDecimal("894792233.72423")
        val expected = Option[BigDecimal](BigDecimals.sqrt(number))
        val actual = BigDecimals.findSqrt(number)
        assert(actual === expected)
        assert(actual.map(x => x * x).get.round(MathContext.DECIMAL64) === number)
      }
    }
  }
  "BigDecimals.mean" when {
    val emptyList = List.empty[BigDecimal]
    s"BigDecimals.mean($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigDecimals.mean(emptyList)
        assert(actual === expected)
      }
    }
    s"BigDecimals.mean(0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigDecimals.mean(List(0))
        assert(actual === expected)
      }
    }
    s"BigDecimals.mean(999)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigDecimals.mean(List(999))
        assert(actual === expected)
      }
    }
    val numbers = List[BigDecimal](1, 2, 2, 3, 3, 3, 4, 6, 10)
    s"BigDecimals.mean($numbers)" should {
      val expected: BigDecimal = numbers.sum / numbers.length
      s"return $expected" in {
        val actual = BigDecimals.mean(numbers)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigDecimal](1, 1, 1, 1, 1)
    s"BigDecimals.mean($numbers)" should {
      val expected: BigDecimal = 1
      s"return $expected" in {
        val actual = BigDecimals.mean(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigDecimal](1, 2, 2, 1)
    s"BigDecimals.mean($numbers)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigDecimals.mean(numbers3)
        assert(actual === expected)
      }
    }
  }

  "BigDecimals.median" when {
    val emptyList = Nil
    s"median($emptyList)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigDecimals.median(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigDecimal](0)
    s"median($numbers0)" should {
      val expected: BigDecimal = 0
      s"return $expected" in {
        val actual = BigDecimals.median(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigDecimal](999)
    s"median($numbers1)" should {
      val expected: BigDecimal = 999
      s"return $expected" in {
        val actual = BigDecimals.median(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigDecimal](1, 2)
    s"median($numbers2)" should {
      val expected: BigDecimal = 1.5
      s"return $expected" in {
        val actual = BigDecimals.median(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigDecimal](1, 2, 3)
    s"median($numbers3)" should {
      val expected: BigDecimal = 2
      s"return $expected" in {
        val actual = BigDecimals.median(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigDecimal](1, 2, 3, 4)
    s"median($numbers4)" should {
      val expected: BigDecimal = (numbers4(1) + numbers4(2)) / 2
      s"return $expected" in {
        val actual = BigDecimals.median(numbers4)
        assert(actual === expected)
      }
    }
    val numbers4unordered = List[BigDecimal](2, 4, 3, 1)
    s"median($numbers4unordered)" should {
      val numbers4ordered = numbers4unordered.sortBy(identity)
      val expected: BigDecimal = (numbers4ordered(1) + numbers4ordered(2)) / 2
      s"return $expected" in {
        val actual = BigDecimals.median(numbers4unordered)
        assert(actual === expected)
      }
    }
    val numbers5 = List[BigDecimal](1, 2, 3, 4, 5)
    s"median($numbers5)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigDecimals.median(numbers5)
        assert(actual === expected)
      }
    }
    val numbers5unordered = List[BigDecimal](2, 3, 5, 4, 1)
    s"median($numbers5unordered)" should {
      val expected: BigDecimal = 3
      s"return $expected" in {
        val actual = BigDecimals.median(numbers5unordered)
        assert(actual === expected)
      }
    }
    val numbers10 = List[BigDecimal](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    s"median($numbers10)" should {
      val expected: BigDecimal = (numbers10(4) + numbers10(5)) / 2
      s"return $expected" in {
        val actual = BigDecimals.median(numbers10)
        assert(actual === expected)
      }
    }
    val numbers11 = List[BigDecimal](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    s"median($numbers11)" should {
      val expected: BigDecimal = 6
      s"return $expected" in {
        val actual = BigDecimals.median(numbers11)
        assert(actual === expected)
      }
    }
  }

  "BigDecimals.mode" when {
    val emptyList = List.empty[BigDecimal]
    s"mode($emptyList)" should {
      val expected = List.empty[BigDecimal]
      s"return $expected" in {
        val actual = BigDecimals.mode(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List[BigDecimal](0)
    s"mode($numbers0)" should {
      val expected = List[BigDecimal](0)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List[BigDecimal](999)
    s"mode($numbers1)" should {
      val expected = List[BigDecimal](999)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List[BigDecimal](1, 2)
    s"mode($numbers2)" should {
      val expected = List[BigDecimal](1, 2)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List[BigDecimal](1, 2, 3)
    s"mode($numbers3)" should {
      val expected = List[BigDecimal](1, 2, 3)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List[BigDecimal](3, 7, 5, 13, 20, 23, 39, 23, 40, 23, 14, 12, 56, 23, 29)
    s"mode($numbers4)" should {
      val expected = List[BigDecimal](23)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers4)
        assert(actual === expected)
      }
    }
    val numbers5 = List[BigDecimal](1, 3, 3, 3, 4, 4, 6, 6, 6, 9)
    s"mode($numbers5)" should {
      val expected = List[BigDecimal](3, 6)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers5)
        assert(actual === expected)
      }
    }
    val numbers6 = List[BigDecimal](1, 1, 2, 3, 3, 3, 3, 5, 5, 7, 7, 7, 7, 100, 101, 101, 101, 101, 8)
    s"mode($numbers6)" should {
      val expected = List[BigDecimal](3, 7, 101)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers6)
        assert(actual === expected)
      }
    }
    val bigIntOfLongMax = BigDecimal(Long.MaxValue)
    val longMaxX2 = bigIntOfLongMax * 2
    val longMaxX3 = bigIntOfLongMax * 3
    val numbers7 = List[BigDecimal](1, 1, 2, longMaxX3, longMaxX3, longMaxX3, longMaxX3, longMaxX3, 5, 5, 7, 7, 7, 7, longMaxX2, longMaxX2, longMaxX2, longMaxX2, longMaxX2, 8)
    s"mode($numbers7)" should {
      val expected = List[BigDecimal](longMaxX2, longMaxX3)
      s"return $expected" in {
        val actual = BigDecimals.mode(numbers7)
        assert(actual === expected)
      }
    }
  }

}
