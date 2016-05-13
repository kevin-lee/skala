package io.kevinlee.skala

import org.scalamock.scalatest.MockFactory
import org.scalatest.WordSpec


/**
  * @author Kevin Lee
  * @since 2016-05-08
  */
class package$Spec extends WordSpec with MockFactory {

  trait SomeResource[T] extends AutoCloseable {
    def run(): T
  }

  class AnotherResource[T](val source: SomeResource[T]) extends AutoCloseable {

    def run(): T = source.run()

    override def close(): Unit = source.close()
  }

  "tryWith" when {

    "tryWith(resource which is null) { // run block }" should {

      "nothing happens to the resource (no NPE)" in {

        val resource: AutoCloseable = null

        tryWith(resource) { someResource =>
        }
      }
    }

    "tryWith(SomeResource) { // run block }" should {

      "call close SomeResource after run block" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).once()
        }

        tryWith(resource) { someResource =>
          someResource.run()
        }

      }
    }

    "val actual = tryWith(SomeResource) { // run block }" should {

      val expected = "Hello"

      s"call close SomeResource after run block and actual === $expected" in {

        val resource = mock[SomeResource[String]]

        inSequence {
          resource.run _ expects() returning expected once()

          resource.close _ expects() once()
        }
        val actual = tryWith(resource) { someResource =>
          someResource.run()
        }
        assert(actual === expected)
      }
    }

    """:
      |    tryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }
      |
    """.stripMargin should {

      "call close SomeResource after run block" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {

          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).twice()
        }

        tryWith(resource) { someResource =>
          tryWith(new AnotherResource[Unit](someResource)) { anotherSource =>
            anotherSource.run()
          }
        }

      }
    }

    """:
      |    val actual = tryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }
      |
    """.stripMargin should {

      val expected = "Hello"

      s"call close SomeResource after run block and actual === $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          anotherResource.run _ expects() onCall { _ => resource.run() } once()
          resource.run _ expects() returning expected once()

          anotherResource.close _ expects() onCall { _ => resource.close() } once()
          resource.close _ expects() twice()
        }

        val actual = tryWith(resource) { someResource =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        assert(actual === expected)
      }
    }

    """anotherResource.close() does not call resource.close()
      |
      |    val actual = tryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }
      |
    """.stripMargin should {

      val expected = "Hello"

      s"call close SomeResource after run block and actual === $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          anotherResource.run _ expects() onCall { _ => resource.run() } once()
          resource.run _ expects() returning expected once()

          anotherResource.close _ expects() once()
          resource.close _ expects() once()
        }

        val actual = tryWith(resource) { someResource =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        assert(actual === expected)
      }
    }

    var count = 0

    case class CountableCloseable() extends AutoCloseable {
      count += 1

      def run(): Unit = ()

      def close(): Unit = ()
    }

    "tryWith(an instantiation of a resource)" should {
      "instantiate it once and use the same one" in {

        tryWith(new CountableCloseable()) { resource =>
          resource.run()
        }

        assert(count == 1)

      }
    }

  }

}
