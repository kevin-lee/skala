package io.kevinlee.skala

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, WordSpec}


/**
  * @author Kevin Lee
  * @since 2018-04-18
  */
@SuppressWarnings(Array("org.wartremover.warts.Any", "org.wartremover.warts.Equals"))
class SkalaPredefSpec extends WordSpec with GeneratorDrivenPropertyChecks with Matchers with SkalaPredefTestRun {

  "PredefSpec.AnyEquals.===" when {

    "(a: Boolean) === (a: Boolean)" should {
      "return true" in {
        forAll { (input: Boolean) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Byte) === (a: Byte)" should {
      "return true" in {
        forAll { (input: Byte) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Short) === (a: Short)" should {
      "return true" in {
        forAll { (input: Short) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Int) === (a: Int)" should {
      "return true" in {
        forAll { (input: Int) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Long) === (a: Long)" should {
      "return true" in {
        forAll { (input: Long) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Float) === (a: Float)" should {
      "return true" in {
        forAll { (input: Float) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Double) === (a: Double)" should {
      "return true" in {
        forAll { (input: Double) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Char) === (a: Char)" should {
      "return true" in {
        forAll { (input: Char) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: String) === (a: String)" should {
      "return true" in {
        forAll { (input: String) =>
          testTwoEquals(input, _ should be(true))
        }
      }
    }

    "(a: Something) === (a: Something)" should {
      "return true" in {
        forAll(Gen.choose(1, Int.MaxValue), Gen.alphaLowerStr, Gen.oneOf(true, false)) { (id, name, valid) =>
          val something = Something(id, name, valid)
          testTwoEquals(something, _ should be(true))
        }
      }
    }


    "(a: Boolean) === (b: Boolean)" should {
      "return false" in {
        forAll { (x: Boolean, y: Boolean) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Byte) === (b: Byte)" should {
      "return false" in {
        forAll { (x: Byte, y: Byte) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Short) === (b: Short)" should {
      "return false" in {
        forAll { (x: Short, y: Short) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Int) === (b: Int)" should {
      "return false" in {
        forAll { (x: Int, y: Int) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Long) === (b: Long)" should {
      "return false" in {
        forAll { (x: Long, y: Long) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Float) === (b: Float)" should {
      "return false" in {
        forAll { (x: Float, y: Float) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Double) === (b: Double)" should {
      "return false" in {
        forAll { (x: Double, y: Double) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Char) === (b: Char)" should {
      "return false" in {
        forAll { (x: Char, y: Char) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: String) === (b: String)" should {
      "return false" in {
        forAll { (x: String, y: String) =>
          whenever(x != y) {
            testTwoEquals(x, y, _ should be(false))
          }
        }
      }
    }

    "(a: Something) === (b: Something)" should {
      "return false" in {
        forAll(
          Gen.choose(1, Int.MaxValue), Gen.alphaLowerStr, Gen.oneOf(true, false),
          Gen.choose(1, Int.MaxValue), Gen.alphaLowerStr, Gen.oneOf(true, false)
        ) { (id, name, valid, id2, name2, valid2) =>
          whenever(id != id2 && name != name2 && valid != valid2) {
            val something = Something(id, name, valid)
            val something2 = Something(id2, name2, valid2)
            testTwoEquals(something, something2, _ should be(false))
          }
        }
      }
    }


  }

  "PredefSpec.AnyEquals.!==" when {

    "(a: Boolean) !== (b: Boolean)" should {
      "return true" in {
        forAll { (x: Boolean, y: Boolean) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Byte) !== (b: Byte)" should {
      "return true" in {
        forAll { (x: Byte, y: Byte) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Short) !== (b: Short)" should {
      "return true" in {
        forAll { (x: Short, y: Short) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Int) !== (b: Int)" should {
      "return true" in {
        forAll { (x: Int, y: Int) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Long) !== (b: Long)" should {
      "return true" in {
        forAll { (x: Long, y: Long) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Float) !== (b: Float)" should {
      "return true" in {
        forAll { (x: Float, y: Float) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Double) !== (b: Double)" should {
      "return true" in {
        forAll { (x: Double, y: Double) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Char) !== (b: Char)" should {
      "return true" in {
        forAll { (x: Char, y: Char) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: String) !== (b: String)" should {
      "return false" in {
        forAll { (x: String, y: String) =>
          whenever(x != y) {
            testTwoNonEquals(x, y, _ should be(true))
          }
        }
      }
    }

    "(a: Something) !== (b: Something)" should {
      "return true" in {
        forAll(
          Gen.choose(1, Int.MaxValue), Gen.alphaLowerStr, Gen.oneOf(true, false),
          Gen.choose(1, Int.MaxValue), Gen.alphaLowerStr, Gen.oneOf(true, false)
        ) { (id, name, valid, id2, name2, valid2) =>
          whenever(id != id2 && name != name2 && valid != valid2) {
            val something = Something(id, name, valid)
            val something2 = Something(id2, name2, valid2)
            testTwoNonEquals(something, something2, _ should be(true))
          }
        }
      }
    }


    "(a: Boolean) !== (a: Boolean)" should {
      "return false" in {
        forAll { (input: Boolean) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Byte) !== (a: Byte)" should {
      "return false" in {
        forAll { (input: Byte) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Short) !== (a: Short)" should {
      "return false" in {
        forAll { (input: Short) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Int) !== (a: Int)" should {
      "return false" in {
        forAll { (input: Int) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Long) !== (a: Long)" should {
      "return false" in {
        forAll { (input: Long) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Float) !== (a: Float)" should {
      "return false" in {
        forAll { (input: Float) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Double) !== (a: Double)" should {
      "return false" in {
        forAll { (input: Double) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Char) !== (a: Char)" should {
      "return false" in {
        forAll { (input: Char) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: String) !== (a: String)" should {
      "return false" in {
        forAll { (input: String) =>
          testTwoNonEquals(input, _ should be(false))
        }
      }
    }

    "(a: Something) !== (a: Something)" should {
      "return false" in {
        forAll(Gen.choose(1, Int.MaxValue), Gen.alphaLowerStr, Gen.oneOf(true, false)) { (id, name, valid) =>
          val something = Something(id, name, valid)
          testTwoNonEquals(something, _ should be(false))
        }
      }
    }

  }

}

final case class Something(id: Int, name: String, valid: Boolean)

trait SkalaPredefTestRun {

  import io.kevinlee.skala.SkalaPredef.AnyEquals
  import org.scalatest.Assertion

  final def testTwoEquals[T](x: T, assertion: Boolean => Assertion): Assertion = assertion(x === x)
  final def testTwoEquals[T](x: T, y: T, assertion: Boolean => Assertion): Assertion = assertion(x === y)

  final def testTwoNonEquals[T](x: T, y: T, assertion: Boolean => Assertion): Assertion = assertion(x !== y)
  final def testTwoNonEquals[T](x: T, assertion: Boolean => Assertion): Assertion = assertion(x !== x)

}
