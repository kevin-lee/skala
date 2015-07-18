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
    "mode()" should {

      "return Seq()" in {

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

  val commonMath = new CommonMath {}

  performance of "Range" in {
    measure method "mode" in {
      using(ranges) in {
        r => commonMath.mode(r)
      }
    }
  }
}