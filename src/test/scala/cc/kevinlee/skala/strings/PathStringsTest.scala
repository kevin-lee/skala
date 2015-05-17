package cc.kevinlee.skala.strings

import org.scalatest.WordSpec

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-05-17
 */
class PathStringsTest extends WordSpec {

  "import PathStrings._" when {
    import PathStrings._
    """"path1" / "path2"""" should {
      """return "path1/path2"""" in {
        val expected = "path1/path2"
        val actual = "path1" / "path2"
        assert(actual === expected)
      }
    }
    """"a"/"b"/"c"""" should {
      """return "a/b/c"""" in {
        val expected = "a/b/c"
        val actual = "a"/"b"/"c"
        assert(actual === expected)
      }
    }
    """"a" / "b" / "c"""" should {
      """return "a/b/c"""" in {
        val expected = "a/b/c"
        val actual = "a" / "b" / "c"
        assert(actual === expected)
      }
    }
    """"a/" / "/b" / "/c/" / "d"""" should {
      """return "a/b/c/d"""" in {
        val expected = "a/b/c/d"
        val actual = "a/" / "/b" / "/c/" / "d"
        assert(actual === expected)
      }
    }
    """"" / "a"""" should {
      """return "/a"""" in {
        val expected = "/a"
        val actual = "" / "a"
        assert(actual === expected)
      }
    }
    """"" / "/b" / "/c/" / "d"""" should {
      """return "/b/c/d"""" in {
        val expected = "/b/c/d"
        val actual = "" / "/b" / "/c/" / "d"
        assert(actual === expected)
      }
    }

    """"path1" \ "path2"""" should {
      """return "path1\path2"""" in {
        val expected = """path1\path2"""
        val actual = "path1" \ "path2"
        assert(actual === expected)
      }
    }
    """"a"\"b"\"c"""" should {
      """return "a\b\c"""" in {
        val expected = """a\b\c"""
        val actual = "a"\"b"\"c"
        assert(actual === expected)
      }
    }
    """"a" \ "b" \ "c"""" should {
      """return "a\b\c"""" in {
        val expected = """a\b\c"""
        val actual = "a" \ "b" \ "c"
        assert(actual === expected)
      }
    }
    """"a\" \ "\b" \ "\c\" \ "d"""" should {
      """return "a\b\c\d"""" in {
        val expected = """a\b\c\d"""
        val actual = "a\\" \ "\\b" \ "\\c\\" \ "d"
        assert(actual === expected)
      }
    }
    """"" \ "a"""" should {
      """return "\a"""" in {
        val expected = """\a"""
        val actual = "" \ "a"
        assert(actual === expected)
      }
    }
    """"" \ "\b" \ "\c\" \ "d"""" should {
      """return "\b\c\d"""" in {
        val expected = """\b\c\d"""
        val actual = "" \ "\\b" \ "\\c\\" \ "d"
        assert(actual === expected)
      }
    }

    "PathString(\"a\")" should {
      "be PathString.path == \"a\"" in {
        assert(PathString("a").path === "a")
      }
    }
  }

}
