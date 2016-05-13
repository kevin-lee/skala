package io.kevinlee.skala.math

import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}

import scala.language.postfixOps

/**
  * @author Lee, Seong Hyun (Kevin)
  * @since 2015-04-13
  */
class package$Spec extends WordSpec with BeforeAndAfterEach {

  /* MathInt.isOdd */

  "implicit class MathInt" when {
    "MathInt.isOdd" when {
      val n = 0

      s"$n.isOdd" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathInt.isOdd" when {
      val n = 1

      s"$n.isOdd" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathInt.isOdd" when {
      val n = 2

      s"$n.isOdd" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathInt.isOdd" when {
      val n = -1

      s"$n.isOdd" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathInt.isOdd" when {
      val n = -2

      s"$n.isOdd" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    /* MathInt.isEven */

    "MathInt.isEven" when {
      val n = 0

      s"$n.isEven" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathInt.isEven" when {
      val n = 1

      s"$n.isEven" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathInt.isEven" when {
      val n = 2

      s"$n.isEven" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathInt.isEven" when {
      val n = -1

      s"$n.isEven" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathInt.isEven" when {
      val n = -2

      s"$n.isEven" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }



    "-1.sqrt" should {
      s"return ${Double.NaN}" in {
        val number = -1
        val actual = number.sqrt
        assert(actual.isNaN)
      }
    }
    "1.sqrt" should {
      "return 1D" in {
        val number = 1
        val expected = 1D
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    "0.sqrt" should {
      "return 0D" in {
        val number = 0
        val expected = 0D
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    "2.sqrt" should {
      val number = 2
      val expected = math.sqrt(number.toDouble)
      s"return $expected" in {
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    "9.sqrt" should {
      val expected = 3D
      s"return $expected" in {
        val number = 9
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    "10.sqrt" should {
      val number = 10
      val expected = math.sqrt(number.toDouble)
      s"return $expected" in {
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    s"${Int.MaxValue}.sqrt" should {
      val number = Int.MaxValue
      val expected = math.sqrt(number.toDouble)
      s"return $expected" in {
        val actual = number.sqrt
        assert(actual === expected)
      }
    }


    /* toOrdinal */


    "1.toOrdinal" should {
      val number = 1
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "2.toOrdinal" should {
      val number = 2
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "3.toOrdinal" should {
      val number = 3
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "4.toOrdinal" should {
      val number = 4
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "5.toOrdinal" should {
      val number = 5
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "10.toOrdinal" should {
      val number = 10
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "11.toOrdinal" should {
      val number = 11
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "12.toOrdinal" should {
      val number = 12
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "13.toOrdinal" should {
      val number = 13
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "14.toOrdinal" should {
      val number = 14
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "20.toOrdinal" should {
      val number = 20
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "21.toOrdinal" should {
      val number = 21
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "22.toOrdinal" should {
      val number = 22
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "23.toOrdinal" should {
      val number = 23
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "24.toOrdinal" should {
      val number = 24
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }

    /* findOrdinal */

    "-1.findOrdinal" should {
      val number = -1
      val expected = None
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "0.findOrdinal" should {
      val number = 0
      val expected = None
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "1.findOrdinal" should {
      val number = 1
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "2.findOrdinal" should {
      val number = 2
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "3.findOrdinal" should {
      val number = 3
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "4.findOrdinal" should {
      val number = 4
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "5.findOrdinal" should {
      val number = 5
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "10.findOrdinal" should {
      val number = 10
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "11.findOrdinal" should {
      val number = 11
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "12.findOrdinal" should {
      val number = 12
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "13.findOrdinal" should {
      val number = 13
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "14.findOrdinal" should {
      val number = 14
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "20.findOrdinal" should {
      val number = 20
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "21.findOrdinal" should {
      val number = 21
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "22.findOrdinal" should {
      val number = 22
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "23.findOrdinal" should {
      val number = 23
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "24.findOrdinal" should {
      val number = 24
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }

  }


  /* MathLong.isOdd */

  "implicit class MathLong" when {
    "MathLong.isOdd" when {
      val n = 0L

      s"$n.isOdd" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathLong.isOdd" when {
      val n = 1L

      s"$n.isOdd" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathLong.isOdd" when {
      val n = 2L

      s"$n.isOdd" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathLong.isOdd" when {
      val n = -1L

      s"$n.isOdd" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    "MathLong.isOdd" when {
      val n = -2L

      s"$n.isOdd" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isOdd

          assert(actual === expected)
        }
      }
    }

    /* isEven */
    "MathLong.isEven" when {
      val n = 0L

      s"$n.isEven" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathLong.isEven" when {
      val n = 1L

      s"$n.isEven" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathLong.isEven" when {
      val n = 2L

      s"$n.isEven" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathLong.isEven" when {
      val n = -1L

      s"$n.isEven" should {
        val expected = false

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    "MathLong.isEven" when {
      val n = -2L

      s"$n.isEven" should {
        val expected = true

        s"be $expected" in {

          val actual = n.isEven

          assert(actual === expected)
        }
      }
    }

    /* sqrt */

    "-1L.sqrt" should {
      "throw java.lang.IllegalArgumentException" in {
        val number = -1L
        val thrown = intercept[IllegalArgumentException] {
          number.sqrt
        }
        assert(thrown.getMessage contains "sqrt can handle only non-negative numbers")
      }
    }
    "1L.sqrt" should {
      "return BigDecimal(1)" in {
        val number = 1L
        val expected: BigDecimal = 1
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    "0L.sqrt" should {
      "return BigDecimal(0)" in {
        val number = 0L
        val expected: BigDecimal = 0
        val actual = number.sqrt
        assert(actual === expected)
      }
    }
    "2L.sqrt" should {
      "return BigDecimal(1.414213562373095...) and actual * actual === BigInt(2)" in {
        val number = 2L
        val expected = "1.414213562373095"
        val actual = number.sqrt
        assert(actual.toString startsWith expected)
        assert((actual * actual) === number)
      }
    }
    "9L.sqrt" should {
      "return BigDecimal(3) and actual * actual === BigInt(9)" in {
        val number = 9L
        val expected: BigDecimal = 3
        val actual = number.sqrt
        assert(actual === expected)
        assert((actual * actual) === number)
      }
    }
    "10L.sqrt" should {
      "return BigDecimal(3.162277660168379...) and actual * actual === (BigInt(10) +- 0.00000000000000000000000000000001)" in {
        val number = 10L
        val expected = "3.162277660168379"
        val actual = number.sqrt
        assert(actual.toString startsWith expected)
        assert((actual * actual) === (BigDecimal(number) +- BigDecimal("0.00000000000000000000000000000001")))
      }
    }
    "89479223372L.sqrt" should {
      "return BigDecimal(299130.779713489...) and actual * actual === (BigInt(89479223372) +- 0.0000000000000000000001)" in {
        val number = 89479223372L
        val expected = "299130.779713489"
        val actual = number.sqrt
        assert(actual.toString startsWith expected)
        assert((actual * actual) === (BigDecimal(number) +- BigDecimal("0.0000000000000000000001")))
      }
    }

    /* toOrdinal */


    "1L.toOrdinal" should {
      val number = 1L
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "2L.toOrdinal" should {
      val number = 2L
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "3L.toOrdinal" should {
      val number = 3L
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "4L.toOrdinal" should {
      val number = 4L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "5L.toOrdinal" should {
      val number = 5L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "10L.toOrdinal" should {
      val number = 10L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "11L.toOrdinal" should {
      val number = 11L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "12L.toOrdinal" should {
      val number = 12L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "13L.toOrdinal" should {
      val number = 13L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "14L.toOrdinal" should {
      val number = 14L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "20L.toOrdinal" should {
      val number = 20L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "21L.toOrdinal" should {
      val number = 21L
      val expected = s"${number}st"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "22L.toOrdinal" should {
      val number = 22L
      val expected = s"${number}nd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "23L.toOrdinal" should {
      val number = 23L
      val expected = s"${number}rd"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }
    "24L.toOrdinal" should {
      val number = 24L
      val expected = s"${number}th"
      s"""return "$expected"""" in {
        val actual = number.toOrdinal
        assert(actual === expected)
      }
    }

    /* findOrdinal */

    "-1L.findOrdinal" should {
      val number = -1L
      val expected = None
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "0L.findOrdinal" should {
      val number = 0L
      val expected = None
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "1L.findOrdinal" should {
      val number = 1L
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "2L.findOrdinal" should {
      val number = 2L
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "3L.findOrdinal" should {
      val number = 3L
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "4L.findOrdinal" should {
      val number = 4L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "5L.findOrdinal" should {
      val number = 5L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "10L.findOrdinal" should {
      val number = 10L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "11L.findOrdinal" should {
      val number = 11L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "12L.findOrdinal" should {
      val number = 12L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "13L.findOrdinal" should {
      val number = 13L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "14L.findOrdinal" should {
      val number = 14L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "20L.findOrdinal" should {
      val number = 20L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "21L.findOrdinal" should {
      val number = 21L
      val expected = Some(s"${number}st")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "22L.findOrdinal" should {
      val number = 22L
      val expected = Some(s"${number}nd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "23L.findOrdinal" should {
      val number = 23L
      val expected = Some(s"${number}rd")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }
    "24L.findOrdinal" should {
      val number = 24L
      val expected = Some(s"${number}th")
      s"return $expected" in {
        val actual = number.findOrdinal
        assert(actual === expected)
      }
    }

  }

}
