package io.kevinlee.skala.strings

//import org.scalatest.{Matchers, WordSpec}

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2017-05-06
  */
object RichStringSpec extends Properties {
  override def tests: List[Test] = List(
    property("test RichString.uncapitalize with one word", testUncapitalizeOneWord),
    property("test RichString.uncapitalize with more than one word", testUncapitalizeMoreThanOneWord),
    property("test RichString.uncapitalize with already lower case String", testUncapitalizeAlreadyLowerCaseString),
    example("test RichString.uncapitalize with an empty String", testUncapitalizeEmptyString),
    example("test RichString.uncapitalize with null String", testUncapitalizeNullString),

    property("test RichString.ToUncapitalize with one word", testToUncapitalizeOneWord),
    property("test RichString.ToUncapitalize with more than one word", testToUncapitalizeMoreThanOneWord),
    property("test RichString.ToUncapitalize with already lower case String", testToUncapitalizeAlreadyLowerCaseString),
    example("test RichString.ToUncapitalize with an empty String", testToUncapitalizeEmptyString),
    example("test RichString.ToUncapitalize with null String", testToUncapitalizeNullString)
  )

  object Gens {
    def genUpperAndLowerChar: Gen[(Char, Char)] =
      Gen.lower.map(c => (c.toUpper, c))

    def genCapitalizedWord: Gen[String] = for {
      c <- Gen.upper
      rest <- Gen.string(Gen.lower, Range.linear(1, 10))
    } yield c.toString + rest

  }

  def testUncapitalizeOneWord: Property = for {
    (firstUpper, firstLower) <- Gens.genUpperAndLowerChar.log("(firstUpper, firstLower)")
    rest <- Gen.string(Gen.lower, Range.linear(1, 10)).log("rest")
  } yield {
    val expected = firstLower.toString + rest
    val input = firstUpper.toString + rest
    val actual = RichString.uncapitalize(input)
    actual ==== expected
  }

  def testUncapitalizeMoreThanOneWord: Property = for {
    (firstUpper, firstLower) <- Gens.genUpperAndLowerChar.log("(firstUpper, firstLower)")
    firstRest <- Gen.string(Gen.lower, Range.linear(1, 10)).log("firstRest")
    moreWords <- Gens.genCapitalizedWord.list(Range.linear(1, 10)).map(_.mkString).log("moreWords")
  } yield {
    val expected = firstLower.toString + firstRest + moreWords
    val input = firstUpper.toString + firstRest + moreWords
    val actual = RichString.uncapitalize(input)
    actual ==== expected
  }

  def testUncapitalizeAlreadyLowerCaseString: Property = for {
    lowerCases <- Gen.string(Gen.lower, Range.linear(1, 10)).log("lowerCases")
  } yield {
    val expected = lowerCases
    val input = lowerCases
    val actual = RichString.uncapitalize(input)
    actual ==== expected
  }

  def testUncapitalizeEmptyString: Result = {
    val expected = ""
    val input = ""
    val actual = RichString.uncapitalize(input)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def testUncapitalizeNullString: Result = {
    val expected: String = null
    val input: String = null
    val actual = RichString.uncapitalize(input)
    actual ==== expected
  }

  def testToUncapitalizeOneWord: Property = for {
    (firstUpper, firstLower) <- Gens.genUpperAndLowerChar.log("(firstUpper, firstLower)")
    rest <- Gen.string(Gen.lower, Range.linear(1, 10)).log("rest")
  } yield {
    import RichString.ToUncapitalize
    val expected = firstLower.toString + rest
    val input = firstUpper.toString + rest
    val actual = input.uncapitalize
    actual ==== expected
  }

  def testToUncapitalizeMoreThanOneWord: Property = for {
    (firstUpper, firstLower) <- Gens.genUpperAndLowerChar.log("(firstUpper, firstLower)")
    firstRest <- Gen.string(Gen.lower, Range.linear(1, 10)).log("firstRest")
    moreWords <- Gens.genCapitalizedWord.list(Range.linear(1, 10)).map(_.mkString).log("moreWords")
  } yield {
    import RichString.ToUncapitalize
    val expected = firstLower.toString + firstRest + moreWords
    val input = firstUpper.toString + firstRest + moreWords
    val actual = input.uncapitalize
    actual ==== expected
  }

  def testToUncapitalizeAlreadyLowerCaseString: Property = for {
    lowerCases <- Gen.string(Gen.lower, Range.linear(1, 10)).log("lowerCases")
  } yield {
    import RichString.ToUncapitalize
    val expected = lowerCases
    val input = lowerCases
    val actual = input.uncapitalize
    actual ==== expected
  }

  def testToUncapitalizeEmptyString: Result = {
    import RichString.ToUncapitalize
    val expected = ""
    val input = ""
    val actual = input.uncapitalize
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  def testToUncapitalizeNullString: Result = {
    import RichString.ToUncapitalize
    val expected: String = null
    val input: String = null
    val actual = input.uncapitalize
    actual ==== expected
  }

}
