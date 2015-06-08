package cc.kevinlee.skala.math

import java.math.MathContext
import cc.kevinlee.{skala => skala}

import org.scalatest.{BeforeAndAfterEach, WordSpec}

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-04-13
 */
class package$Spec extends WordSpec with BeforeAndAfterEach {

  "package$Test" when {

    "isOdd(0)" should {
      val expected = false
      s"return $expected" in {
        val number = 0
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }
    "isOdd(1)" should {
      val expected = true
      s"return $expected" in {
        val number = 1
        val actual = isOdd(1)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }

    "isOdd(2)" should {
      val expected = false
      s"return $expected" in {
        val number = 2
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }
    "isOdd(3)" should {
      val expected = true
      s"return $expected" in {
        val number = 3
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }

    "isEven(0)" should {
      val expected = true
      s"return $expected" in {
        val number = 0
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }
    "isEven(1)" should {
      val expected = false
      s"return $expected" in {
        val number = 1
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }
    "isEven(2)" should {
      val expected = true
      s"return $expected" in {
        val number = 2
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }
    "isEven(3)" should {
      val expected = false
      s"return $expected" in {
        val number = 3
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }

    "isOdd(0L)" should {
      val expected = false
      s"return $expected" in {
        val number = 0L
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }
    "isOdd(1L)" should {
      val expected = true
      s"return $expected" in {
        val number = 1L
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }

    "isOdd(2L)" should {
      val expected = false
      s"return $expected" in {
        val number = 2L
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }
    "isOdd(3L)" should {
      val expected = true
      s"return $expected" in {
        val number = 3L
        val actual = isOdd(number)
        assert(actual === expected)
        assert(number.isOdd === expected)
      }
    }

    "isEven(0L)" should {
      val expected = true
      s"return $expected" in {
        val number = 0L
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }
    "isEven(1L)" should {
      val expected = false
      s"return $expected" in {
        val number = 1L
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }
    "isEven(2L)" should {
      val expected = true
      s"return $expected" in {
        val number = 2L
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }
    "isEven(3L)" should {
      val expected = false
      s"return $expected" in {
        val number = 3L
        val actual = isEven(number)
        assert(actual === expected)
        assert(number.isEven === expected)
      }
    }

    "sqrt(-1)" should {
      s"return ${Double.NaN}" in {
        val number = -1
        val actual = sqrt(number)
        assert(actual.isNaN)
      }
    }
    "sqrt(1)" should {
      "return 1D" in {
        val number = 1
        val expected = 1D
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(0)" should {
      "return 0D" in {
        val number = 0
        val expected = 0D
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(2)" should {
      val number = 2
      val expected = math.sqrt(number)
      s"return $expected" in {
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(9)" should {
      val expected = 3D
      s"return $expected" in {
        val number = 9
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(10)" should {
      val number = 10
      val expected = math.sqrt(number)
      s"return $expected" in {
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    s"sqrt(${Int.MaxValue})" should {
      val number = Int.MaxValue
      val expected = math.sqrt(number)
      s"return $expected" in {
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }

    "sqrt(-1L)" should {
      "throw java.lang.IllegalArgumentException" in {
        val number = -1L
        val thrown = intercept[IllegalArgumentException] {
          sqrt(number)
        }
        assert(thrown.getMessage contains "sqrt can handle only non-negative numbers")
      }
    }
    "sqrt(1L)" should {
      "return BigDecimal(1)" in {
        val number = 1L
        val expected: BigDecimal = 1
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(0L)" should {
      "return BigDecimal(0)" in {
        val number = 0L
        val expected: BigDecimal = 0
        val actual = sqrt(number)
        assert(actual === expected)
      }
    }
    "sqrt(2L)" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
        val number = 2L
        val expected = "1.414213562373095"
        val actual = sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual) === number)
      }
    }
    "sqrt(9L)" should {
      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
        val number = 9L
        val expected: BigDecimal = 3
        val actual = sqrt(number)
        assert(actual === expected)
        assert((actual * actual) === number)
      }
    }
    "sqrt(10L)" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === BigInt(10)" in {
        val number = 10L
        val expected = "3.162277660168379"
        val actual = sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual).round(MathContext.DECIMAL64) === number)
      }
    }
    "sqrt(89479223372L)" should {
      "return BigDecimal(299130.779713489...) and actual * actual === BigInt(89479223372)" in {
        val number = 89479223372L

        val expected = "299130.779713489"
        val actual = sqrt(number)
        assert(actual.toString startsWith expected)
        assert((actual * actual).round(MathContext.DECIMAL64) === number)
      }
    }

    "toOrdinal(1)" should {
      val number = 1
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(2)" should {
      val number = 2
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(3)" should {
      val number = 3
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(4)" should {
      val number = 4
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(5)" should {
      val number = 5
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(10)" should {
      val number = 10
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(11)" should {
      val number = 11
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(12)" should {
      val number = 12
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(13)" should {
      val number = 13
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(14)" should {
      val number = 14
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(20)" should {
      val number = 20
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(21)" should {
      val number = 21
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(22)" should {
      val number = 22
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(23)" should {
      val number = 23
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(24)" should {
      val number = 24
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }

    "toOrdinal(1L)" should {
      val number = 1L
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(2L)" should {
      val number = 2L
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(3L)" should {
      val number = 3L
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(4L)" should {
      val number = 4L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(5L)" should {
      val number = 5L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(10L)" should {
      val number = 10L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(11L)" should {
      val number = 11L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(12L)" should {
      val number = 12L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(13L)" should {
      val number = 13L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(14L)" should {
      val number = 14L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(20L)" should {
      val number = 20L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(21L)" should {
      val number = 21L
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(22L)" should {
      val number = 22L
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(23L)" should {
      val number = 23L
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }
    "toOrdinal(24L)" should {
      val number = 24L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = toOrdinal(number)
        assert(actual === expected)
      }
    }

    "findOrdinal(-1)" should {
      val number = -1
      val expected = None
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(0)" should {
      val number = 0
      val expected = None
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(1)" should {
      val number = 1
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(2)" should {
      val number = 2
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(3)" should {
      val number = 3
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(4)" should {
      val number = 4
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(5)" should {
      val number = 5
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(10)" should {
      val number = 10
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(11)" should {
      val number = 11
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(12)" should {
      val number = 12
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(13)" should {
      val number = 13
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(14)" should {
      val number = 14
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(20)" should {
      val number = 20
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(21)" should {
      val number = 21
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(22)" should {
      val number = 22
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(23)" should {
      val number = 23
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(24)" should {
      val number = 24
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }

    "findOrdinal(-1L)" should {
      val number = -1L
      val expected = None
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(0L)" should {
      val number = 0L
      val expected = None
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(1L)" should {
      val number = 1L
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(2L)" should {
      val number = 2L
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(3L)" should {
      val number = 3L
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(4L)" should {
      val number = 4L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(5L)" should {
      val number = 5L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(10L)" should {
      val number = 10L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(11L)" should {
      val number = 11L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(12L)" should {
      val number = 12L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(13L)" should {
      val number = 13L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(14L)" should {
      val number = 14L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(20L)" should {
      val number = 20L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(21L)" should {
      val number = 21L
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(22L)" should {
      val number = 22L
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(23L)" should {
      val number = 23L
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
    "findOrdinal(24L)" should {
      val number = 24L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = findOrdinal(number)
        assert(actual === expected)
      }
    }
  }
}
