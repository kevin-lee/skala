package cc.kevinlee.skala.math

import org.scalameter.api._
import org.scalatest.WordSpec

import scala.util.Random

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-07-18
 */
class CommonMathTest extends WordSpec {

  "CommonMath" when {
    val emptyList = List.empty[Int]
    s"mode($emptyList)" should {
      val expected = List.empty[Int]
      s"return $expected" in {
        val actual = CommonMath.mode(emptyList)
        assert(actual === expected)
      }
    }
    val numbers0 = List(0)
    s"mode($numbers0)" should {
      val expected = List(0)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers0)
        assert(actual === expected)
      }
    }
    val numbers1 = List(999)
    s"mode($numbers1)" should {
      val expected = List(999)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers1)
        assert(actual === expected)
      }
    }
    val numbers2 = List(1, 2)
    s"mode($numbers2)" should {
      val expected = List(1, 2)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers2)
        assert(actual === expected)
      }
    }
    val numbers3 = List(1, 2, 3)
    s"mode($numbers3)" should {
      val expected = List(1, 2, 3)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers3)
        assert(actual === expected)
      }
    }
    val numbers4 = List(3, 7, 5, 13, 20, 23, 39, 23, 40, 23, 14, 12, 56, 23, 29)
    s"mode($numbers4)" should {
      val expected = List(23)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers4)
        assert(actual === expected)
      }
    }
    val numbers5 = List(1, 3, 3, 3, 4, 4, 6, 6, 6, 9)
    s"mode($numbers5)" should {
      val expected = List(3, 6)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers5)
        assert(actual === expected)
      }
    }
    val numbers6 = List(1, 1, 2, 3, 3, 3, 3, 5, 5, 7, 7, 7, 7, 100, 101, 101, 101, 101, 8)
    s"mode($numbers6)" should {
      val expected = List(3, 7, 101)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers6)
        assert(actual === expected)
      }
    }
    val intMax = Int.MaxValue
    val someInt1 = intMax >> 1
    val someInt2 = intMax >> 2
    val numbers7 = List(1, 1, 2, someInt1, someInt1, someInt1, someInt1, someInt1, 5, 5, 7, 7, 7, 7, someInt2, someInt2, someInt2, someInt2, someInt2, 8)
    s"mode($numbers7)" should {
      val expected = List(someInt2, someInt1)
      s"return $expected" in {
        val actual = CommonMath.mode(numbers7)
        assert(actual === expected)
      }
    }
  }
}


object CommonMathRangeBenchmark extends PerformanceTest.Quickbenchmark {
  val sizes = Gen.range("size")(10, 100000, 5000)
  val upTo = 10
  val numberPool = (0 to upTo).toVector
  val random = new Random(999)

  def getRandomNumbers(howMany: Int): Vector[BigInt] = Random.shuffle(
    (0 until howMany).map(_ => BigInt(numberPool(random.nextInt(upTo)))).toVector ++ Vector.fill[BigInt](999)(7)
  )

  val ranges = for {
    size <- sizes
  } yield getRandomNumbers(size)

  performance of "Range" in {
    measure method "mode" in {
      using(ranges) in {
        r => CommonMath.mode(r)
      }
    }
  }
}