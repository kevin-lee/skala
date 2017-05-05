package io.kevinlee.skala.strings

import org.scalatest.{Matchers, WordSpec}

/**
  * @author Kevin Lee
  * @since 2017-05-06
  */
class RichStringSpec extends WordSpec with Matchers {

  "RichString" when {
    val input = "Test"
    s"""uncapitalize("$input")""" should {
      val expected = "test"
      s"""return "$expected"""" in {
        val actual = RichString.uncapitalize(input)

        actual should be (expected)
      }
    }

    val input2 = "TestSomething"
    s"""uncapitalize("$input2")""" should {
      val expected = "testSomething"
      s"""return "$expected"""" in {
        val actual = RichString.uncapitalize(input2)

        actual should be (expected)
      }
    }

    val input3 = "test"
    s"""uncapitalize("$input3")""" should {
      val expected = input3
      s"""return "$expected"""" in {
        val actual = RichString.uncapitalize(input3)

        actual should be (expected)
      }
    }

    val input4 = ""
    s"""uncapitalize("$input4")""" should {
      val expected = ""
      s"""return "$expected"""" in {
        val actual = RichString.uncapitalize(input4)

        actual should be (expected)
      }
    }

    val input5 = null
    s"""uncapitalize($input5)""" should {
      s"""throw NullPointerException""" in {

        a [NullPointerException] should be thrownBy RichString.uncapitalize(input5)

      }
    }
  }

  import RichString.ToUncapitalize
  "ToUncapitalize" when {
    s""""Test".uncapitalize""" should {
      val expected = "test"
      s"""return "$expected"""" in {
        val actual = "Test".uncapitalize

        actual should be (expected)
      }
    }

    val input2 = "TestSomething"
    s""""$input2".uncapitalize""" should {
      val expected = "testSomething"
      s"""return "$expected"""" in {
        val actual = input2.uncapitalize

        actual should be (expected)
      }
    }

    val input3 = "test"
    s""""$input3".uncapitalize""" should {
      val expected = input3
      s"""return "$expected"""" in {
        val actual = input3.uncapitalize

        actual should be (expected)
      }
    }

    val input4 = ""
    s""""$input4".uncapitalize""" should {
      val expected = ""
      s"""return "$expected"""" in {
        val actual = input4.uncapitalize

        actual should be (expected)
      }
    }

    val input5: String = null
    s"""$input5.uncapitalize""" should {
      s"""throw NullPointerException""" in {

        a [NullPointerException] should be thrownBy input5.uncapitalize

      }
    }
  }
}
