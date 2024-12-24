package io.kevinlee.skala

import hedgehog._
import hedgehog.runner._

import SkalaPredef._

/**
  * @author Kevin Lee
  * @since 2018-04-18
  */
@SuppressWarnings(Array("org.wartremover.warts.Equals", "org.wartremover.warts.StringPlusAny"))
object SkalaPredefSpec extends Properties {

  def genSomething: Gen[Something] = for {
    id <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue))
    name <- Gen.string(Gen.alphaNum, Range.linear(1, 50))
    valid <- Gen.boolean
  } yield Something(id, name, valid)

  def genSomethingDifferent(a: Something): Gen[Something] = for {
    id <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(b => if (a.id == b) b + 1 else b)
    name <- Gen.string(Gen.alphaNum, Range.linear(1, 50)).map(b => if (a.name == b) b + "a" else b)
    valid <- Gen.boolean.map(b => if (a.valid == b) !b else b)
  } yield Something(id, name, valid)

  val anyEquals: String = "PredefSpec.AnyEquals"

  override def tests: List[Test] = List(
    property(
      s"$anyEquals.=== when (a: Boolean) === (a: Boolean) should return true",
      `testSkalaPredefAnyEqualsBoolean===Boolean`
    ),
    property(
      s"$anyEquals.=== when (a: Byte) === (a: Byte) should return true",
      `testSkalaPredefAnyEqualsByte===Byte`
    ),
    property(
      s"$anyEquals.=== when (a: Short) === (a: Short) should return true",
      `testSkalaPredefAnyEqualsShort===Short`
    ),
    property(
      s"$anyEquals.=== when (a: Int) === (a: Int) should return true",
      `testSkalaPredefAnyEqualsInt===Int`
    ),
    property(
      s"$anyEquals.=== when (a: Long) === (a: Long) should return true",
      `testSkalaPredefAnyEqualsLong===Long`
    ),
    property(
      s"$anyEquals.=== when (a: Float) === (a: Float) should return true",
      `testSkalaPredefAnyEqualsFloat===Float`
    ),
    property(
      s"$anyEquals.=== when (a: Double) === (a: Double) should return true",
      `testSkalaPredefAnyEqualsDouble===Double`
    ),
    property(
      s"$anyEquals.=== when (a: Char) === (a: Char) should return true",
      `testSkalaPredefAnyEqualsChar===Char`
    ),
    property(
      s"$anyEquals.=== when (a: String) === (a: String) should return true",
      `testSkalaPredefAnyEqualsString===String`
    ),
    property(
      s"$anyEquals.=== when (a: Something) === (a: Something) should return true",
      `testSkalaPredefAnyEqualsSomething===Something`
    ),
    property(
      s"$anyEquals.=== when (a: Boolean) === (b: Boolean) should return false",
      `testSkalaPredefAnyEquals(a: Boolean)===(b: Boolean)`
    ),
    property(
      s"$anyEquals.=== when (a: Byte) === (b: Byte) should return false",
      `testSkalaPredefAnyEquals(a: Byte)===(b: Byte)`
    ),
    property(
      s"$anyEquals.=== when (a: Short) === (b: Short) should return false",
      `testSkalaPredefAnyEquals(a: Short)===(b: Short)`
    ),
    property(
      s"$anyEquals.=== when (a: Int) === (b: Int) should return false",
      `testSkalaPredefAnyEquals(a: Int)===(b: Int)`
    ),
    property(
      s"$anyEquals.=== when (a: Long) === (b: Long) should return false",
      `testSkalaPredefAnyEquals(a: Long)===(b: Long)`
    ),
    property(
      s"$anyEquals.=== when (a: Float) === (b: Float) should return false",
      `testSkalaPredefAnyEquals(a: Float)===(b: Float)`
    ),
    property(
      s"$anyEquals.=== when (a: Double) === (b: Double) should return false",
      `testSkalaPredefAnyEquals(a: Double)===(b: Double)`
    ),
    property(
      s"$anyEquals.=== when (a: Char) === (b: Char) should return false",
      `testSkalaPredefAnyEquals(a: Char)===(b: Char)`
    ),
    property(
      s"$anyEquals.=== when (a: String) === (b: String) should return false",
      `testSkalaPredefAnyEquals(a: String)===(b: String)`
    ),
    property(
      s"$anyEquals.=== when (a: Something) === (b: Something) should return false",
      `testSkalaPredefAnyEquals(a: Something)===(b: Something)`
    )
  )

  def `testSkalaPredefAnyEqualsBoolean===Boolean`: Property = for {
    a <- Gen.boolean.log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsByte===Byte`: Property = for {
    a <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsShort===Short`: Property = for {
    a <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsInt===Int`: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsLong===Long`: Property = for {
    a <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsFloat===Float`: Property = for {
    a <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(_.toFloat).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsDouble===Double`: Property = for {
    a <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsChar===Char`: Property = for {
    a <- Gen.char('a', 'z').log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsString===String`: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEqualsSomething===Something`: Property = for {
    a <- genSomething.log("a")
  } yield {
    Result.diff(a, a)(_ === _)
  }

  def `testSkalaPredefAnyEquals(a: Boolean)===(b: Boolean)`: Property = for {
    a <- Gen.boolean.log("a")
    b <- Gen.constant(!a).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Byte)===(b: Byte)`: Property = for {
    a <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).log("a")
    b <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).map(b => if (a == b) (b + 1).toByte else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Short)===(b: Short)`: Property = for {
    a <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).log("a")
    b <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).map(b => if (a == b) (b + 1).toShort else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Int)===(b: Int)`: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Long)===(b: Long)`: Property = for {
    a <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).log("a")
    b <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Float)===(b: Float)`: Property = for {
    a <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(_.toFloat).log("a")
    b <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(b => if (a == b) b + 1 else b).map(_.toFloat).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Double)===(b: Double)`: Property = for {
    a <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).log("a")
    b <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Char)===(b: Char)`: Property = for {
    a <- Gen.char('a', 'z').log("a")
    b <- Gen.char('a', 'z').map(b => if (a == b) (b + 1).toChar else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: String)===(b: String)`: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).log("a")
    b <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }

  def `testSkalaPredefAnyEquals(a: Something)===(b: Something)`: Property = for {
    a <- genSomething.log("a")
    b <- genSomethingDifferent(a).log("b")
  } yield {
    Result.diff(a, b)((a, b) => !(a === b))
  }



  def `testSkalaPredefAnyEqualsBoolean!==Boolean`: Property = for {
    a <- Gen.boolean.log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsByte!==Byte`: Property = for {
    a <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsShort!==Short`: Property = for {
    a <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsInt!==Int`: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsLong!==Long`: Property = for {
    a <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsFloat!==Float`: Property = for {
    a <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(_.toFloat).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsDouble!==Double`: Property = for {
    a <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsChar!==Char`: Property = for {
    a <- Gen.char('a', 'z').log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsString!==String`: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEqualsSomething!==Something`: Property = for {
    a <- genSomething.log("a")
  } yield {
    Result.diff(a, a)((a1, a2) => !(a1 !== a2))
  }

  def `testSkalaPredefAnyEquals(a: Boolean)!==(b: Boolean)`: Property = for {
    a <- Gen.boolean.log("a")
    b <- Gen.boolean.map(b => if (a == b) !b else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Byte)!==(b: Byte)`: Property = for {
    a <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).log("a")
    b <- Gen.byte(Range.linear(Byte.MinValue, Byte.MaxValue)).map(b => if (a == b) (b + 1).toByte else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Short)!==(b: Short)`: Property = for {
    a <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).log("a")
    b <- Gen.short(Range.linear(Short.MinValue, Short.MaxValue)).map(b => if (a == b) (b + 1).toShort else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Int)!==(b: Int)`: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Long)!==(b: Long)`: Property = for {
    a <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).log("a")
    b <- Gen.long(Range.linear(Long.MinValue, Long.MaxValue)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Float)!==(b: Float)`: Property = for {
    a <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(_.toFloat).log("a")
    b <- Gen.double(Range.linearFrac(Float.MinValue.toDouble, Float.MaxValue.toDouble)).map(b => if (a == b) b + 1 else b).map(_.toFloat).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Double)!==(b: Double)`: Property = for {
    a <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).log("a")
    b <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue)).map(b => if (a == b) b + 1 else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Char)!==(b: Char)`: Property = for {
    a <- Gen.char('a', 'z').log("a")
    b <- Gen.char('a', 'z').map(b => if (a == b) (b + 1).toChar else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: String)!==(b: String)`: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).log("a")
    b <- Gen.string(Gen.alphaNum, Range.linear(1, 100)).map(b => if (a == b) b + "a" else b).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

  def `testSkalaPredefAnyEquals(a: Something)!==(b: Something)`: Property = for {
    a <- genSomething.log("a")
    b <- genSomethingDifferent(a).log("b")
  } yield {
    Result.diff(a, b)(_ !== _)
  }

}

final case class Something(id: Int, name: String, valid: Boolean)

//trait SkalaPredefTestRun {
//
//  import io.kevinlee.skala.SkalaPredef.AnyEquals
//  import org.scalatest.Assertion
//
//  final def testTwoEquals[T](x: T, assertion: Boolean => Assertion): Assertion = assertion(x === x)
//  final def testTwoEquals[T](x: T, y: T, assertion: Boolean => Assertion): Assertion = assertion(x === y)
//
//  final def testTwoNonEquals[T](x: T, y: T, assertion: Boolean => Assertion): Assertion = assertion(x !== y)
//  final def testTwoNonEquals[T](x: T, assertion: Boolean => Assertion): Assertion = assertion(x !== x)
//
//}
