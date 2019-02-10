package io.kevinlee.skala.util

import io.kevinlee.skala.util.SideEffect.tryWith
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.util.{Failure, Success, Try}

/**
  * @author Kevin Lee
  * @since 2016-05-08
  */
@SuppressWarnings(
  Array(
    "org.wartremover.warts.Any",
    "org.wartremover.warts.NonUnitStatements",
    "org.wartremover.warts.Nothing",
    "org.wartremover.warts.Null",
    "org.wartremover.warts.Product",
    "org.wartremover.warts.PublicInference",
    "org.wartremover.warts.Throw",
    "org.wartremover.warts.Var"
  )
)
class SideEffectSpec extends WordSpec with Matchers with MockFactory {

  trait SomeResource[T] extends AutoCloseable {
    def run(): T
  }

  class AnotherResource[T](val source: SomeResource[T]) extends AutoCloseable {

    def run(): T = source.run()

    override def close(): Unit = source.close()
  }

  case object TryTestException extends RuntimeException

  case object AnotherTryTestException extends RuntimeException

  "TryWith.tryWith" when {

    "tryWith(resource which is null) { // run block }" should {

      "throw NullPointerException" in {

        val resource: SomeResource[Nothing] = null

        a[NullPointerException] should be thrownBy {
          tryWith(resource) { someResource =>
            someResource.run()
          }
        }

      }
    }

    "tryWith(SomeResource) { // run block }" should {

      val expected = ()
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit).expects().returning(()).once()

          (resource.close _: () => Unit).expects().returning(()).once()
        }

        val actual = tryWith(resource) { someResource =>
          someResource.run()
        }

        actual should be(expected)

      }
    }

    "val actual = tryWith(SomeResource) { // run block }" should {

      val expected = "Hello"

      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[String]]

        inSequence {
          (resource.run _: () => String) expects() returning expected once()

          (resource.close _: () => Unit) expects() once()
        }
        val actual = tryWith(resource) { someResource =>
          someResource.run()
        }
        actual should be(expected)
      }
    }

    """:
      |    tryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }""".stripMargin should {

      val expected = ()
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {

          (resource.run _: () => Unit).expects().returning(()).once()

          (resource.close _: () => Unit).expects().returning(()).twice()
        }

        val actual = tryWith(resource) { someResource =>
          tryWith(new AnotherResource[Unit](someResource)) { anotherSource =>
            anotherSource.run()
          }
        }

        actual should be(expected)

      }
    }

    """:
      |    val actual = tryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }""".stripMargin should {

      val expected = "Hello"

      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          (anotherResource.run _: () => String) expects() onCall { _ => resource.run() } once()
          (resource.run _: () => String) expects() returning expected once()

          (anotherResource.close _: () => Unit) expects() onCall { _ => resource.close() } once()
          (resource.close _: () => Unit) expects() twice()
        }

        val actual = tryWith(resource) { _ =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        actual should be(expected)
      }
    }

    """:
      |    val actual = tryWith(SomeResource) { someResource =>
      |      // calling a method in someResource
      |      tryWith(AnotherResource) { anotherResource =>
      |        // calling a method in anotherResource
      |      }
      |    }""".stripMargin should {

      val expected = "Hello"

      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          (resource.run _: () => String) expects() once()
          (anotherResource.run _: () => String) expects() returning expected once()

          (anotherResource.close _: () => Unit) expects() once()
          (resource.close _: () => Unit) expects() once()
        }

        val actual = tryWith(resource) { someResource =>
          someResource.run()
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        actual should be(expected)
      }
    }

    """val actual = tryWith(SomeResource) { someResource =>
      |      tryWith(AnotherResource) { someOtherResource =>
      |        // run block
      |      }
      |    }""".stripMargin should {

      val expected = "Hello"

      s"call close AnotherResource and close SomeResource after run block then return $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          (anotherResource.run _: () => String)expects() onCall { _ => resource.run() } once()
          (resource.run _: () => String) expects() returning expected once()

          (anotherResource.close _: () => Unit) expects() once()
          (resource.close _: () => Unit) expects() once()
        }

        val actual = tryWith(resource) { _ =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        actual should be(expected)
      }
    }

    "tryWith(SomeResource) { someResource => someResource.run() // throwing TryTestException }" should {

      s"call close SomeResource after run block and throw ${TryTestException.getClass.getSimpleName}" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit) expects() throws TryTestException

          (resource.close _: () => Unit).expects().returning(()).once()
        }

        a[TryTestException.type] should be thrownBy {
          tryWith(resource) { someResource =>
            someResource.run()
          }
        }

      }
    }

    """tryWith(SomeResource) { someResource =>
      |         tryWith(new AutoCloseable {
      |           override def close(): Unit = ()
      |         }) { anotherResource =>
      |           someResource.run() // throws TryTestException here
      |         }
      |         throw AnotherTryTestException
      |       }""".stripMargin should {

      s"call close SomeResource after run block and throw ${TryTestException.getClass.getSimpleName}" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit) expects() throwing (TryTestException) once() //throws TryTestException

          (resource.close _: () => Unit).expects().returning(()).once()
        }

        a[TryTestException.type] should be thrownBy {
          tryWith(resource) { someResource =>
            tryWith(new AutoCloseable {
              override def close(): Unit = ()
            }) { _ =>
              someResource.run()
            }
            throw AnotherTryTestException
          }
        }
      }
    }

    """tryWith(resource) { someResource =>
      |         someResource.run() // throws TryTestException
      |
      |         tryWith(resource2) { anotherResource =>
      |           anotherResource.run() // should never be called
      |         }
      |       }""".stripMargin should {

      s"call close SomeResource after run block and throw $TryTestException" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit) expects() throws TryTestException

          (resource.close _: () => Unit).expects().returning(()).once()
        }
        val resource2 = mock[SomeResource[Unit]]

        inSequence {
          (resource2.run _: () => Unit) expects() never()

          (resource2.close _: () => Unit).expects().returning(()).never()
        }

        a[TryTestException.type] should be thrownBy {
          tryWith(resource) { someResource =>
            someResource.run()

            tryWith(resource2) { anotherResource =>
              anotherResource.run()
            }
          }
        }
      }
    }

    "tryWith(an instantiation of a resource)" should {
      val expected = ()
      s"instantiate it once and use the same one and return $expected" in {

        var count = 0

        case class CountableCloseable() extends AutoCloseable {
          count += 1

          def run(): Unit = ()

          def close(): Unit = ()
        }

        val actual = tryWith(new CountableCloseable()) { resource =>
          resource.run()
        }

        count should be(1)

        actual should be(expected)

      }
    }

    "tryWith(an instantiation of a resource that throws a RuntimException when close is called){ // block }(logger)" should {
      val expected = 999
      s"instantiate it once and use the same one and return $expected and the exception when closing should be handled by the logger" in {

        var count = 0
        val expectedException = new RuntimeException()

        case class CountableCloseable() extends AutoCloseable {
          count += 1

          def run(): Int = 999

          def close(): Unit = throw expectedException
        }

        try {
          tryWith(CountableCloseable()) { resource =>
            resource.run()
          }

          fail("CountableCloseable should have been thrown but it did not")
        } catch {
          case ex: Throwable =>
            count should be (1)
            ex should be (expectedException)
        }

      }
    }

  }


  "Try(tryWith)" when {

    "Try(tryWith(resource which is null) { // run block })" should {

      "return Failure(NPE)" in {

        val resource: SomeResource[Nothing] = null

        val actual = Try(tryWith(resource) { someResource =>
          someResource.run()
        })

        actual match {
          case expected@Failure(npe: NullPointerException) =>
          case _ => fail(s"$actual is not ${classOf[NullPointerException]}")
        }
      }
    }

    "Try(tryWith(SomeResource) { // run block })" should {

      val expected = Success(())
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit).expects().returning(()).once()

          (resource.close _: () => Unit).expects().returning(()).once()
        }

        val actual = Try(tryWith(resource) { someResource =>
          someResource.run()
        })

        actual should be(expected)

      }
    }

    "val actual = tryWith(SomeResource) { // run block }" should {

      val expectedString = "Hello"
      val expected = Success(expectedString)

      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[String]]

        inSequence {
          (resource.run _: () => String) expects() returning expectedString once()

          (resource.close _: () => Unit) expects() once()
        }
        val actual = Try(tryWith(resource) { someResource =>
          someResource.run()
        })
        actual should be(expected)
      }
    }

    """:
      |    tryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }""".stripMargin should {

      val expected = Success(())
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {

          (resource.run _: () => Unit).expects().returning(()).once()

          (resource.close _: () => Unit).expects().returning(()).twice()
        }

        val actual = Try(tryWith(resource) { someResource =>
          tryWith(new AnotherResource[Unit](someResource)) { anotherSource =>
            anotherSource.run()
          }
        })

        actual should be(expected)

      }
    }

    """val actual = tryWith(SomeResource) {
      |                      tryWith(AnotherResource) {
      |                        // run block
      |                      }
      |                    }""".stripMargin should {

      val expectedString = "Hello"
      val expected = Success(expectedString)

      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          (anotherResource.run _: () => String) expects() onCall { _ => resource.run() } once()
          (resource.run _: () => String) expects() returning expectedString once()

          (anotherResource.close _: () => Unit) expects() onCall { _ => resource.close() } once()
          (resource.close _: () => Unit) expects() twice()
        }

        val actual = Try(tryWith(resource) { _ =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        })
        actual should be(expected)
      }
    }

    """val actual = Try(tryWith(resource) { someResource =>
      |                      tryWith(anotherResource) { someOtherResource =>
      |                        someOtherResource.run()
      |                      }
      |                    })""".stripMargin should {

      val expectedString = "Hello"
      val expected = Success(expectedString)

      s"call close anotherResource and close resource after run block then return $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          (anotherResource.run _: () => String) expects() onCall { _ => resource.run() } once()
          (resource.run _: () => String) expects() returning expectedString once()

          (anotherResource.close _: () => Unit) expects() once()
          (resource.close _: () => Unit) expects() once()
        }

        val actual = Try(tryWith(resource) { _ =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        })
        actual should be(expected)
      }
    }


    "tryWith(SomeResource) { someResource => someResource.run() // throwing TryTestException }" should {

      val expected = Failure(TryTestException)
      s"call close SomeResource after run block and return $expected" in {


        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit) expects() throws TryTestException

          (resource.close _: () => Unit).expects().returning(()).once()
        }

        val actual = Try(tryWith(resource) { someResource =>
          someResource.run()
        })

        actual should be(expected)

      }
    }

    """Try(tryWith(SomeResource) { someResource =>
      |         tryWith(new AutoCloseable {
      |           override def close(): Unit = ()
      |         }) { anotherResource =>
      |           someResource.run() // throws TryTestException here
      |         }
      |         throw AnotherTryTestException
      |       })""".stripMargin should {

      val expected = Failure(TryTestException)
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit) expects() throwing (TryTestException) once() //throws TryTestException

          (resource.close _: () => Unit).expects().returning(()).once()
        }

        val actual = Try(tryWith(resource) { someResource =>
          tryWith(new AutoCloseable {
            override def close(): Unit = ()
          }) { _ =>
            someResource.run()
          }
          throw AnotherTryTestException
        })

        actual should be(expected)
      }
    }

    """tryWith(resource) { someResource =>
      |         someResource.run() // throws TryTestException
      |
      |         tryWith(resource2) { anotherResource =>
      |           anotherResource.run() // should never be called
      |         }
      |       }""".stripMargin should {

      val expected = Failure(TryTestException)

      s"call close SomeResource after run block and throw $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _: () => Unit) expects() throws TryTestException

          (resource.close _: () => Unit).expects().returning(()).once()
        }
        val resource2 = mock[SomeResource[Unit]]

        inSequence {
          (resource2.run _: () => Unit) expects() never()

          (resource2.close _: () => Unit).expects().returning(()).never()
        }

        val actual = Try(tryWith(resource) { someResource =>
          someResource.run()

          tryWith(resource2) { anotherResource =>
            anotherResource.run()
          }
        })

        actual should be(expected)
      }
    }

    "tryWith(an instantiation of a resource)" should {
      val expected = Success(())
      s"instantiate it once and use the same one and return $expected" in {

        var count = 0

        case class CountableCloseable() extends AutoCloseable {
          count += 1

          def run(): Unit = ()

          def close(): Unit = ()
        }

        val actual = Try(tryWith(new CountableCloseable()) { resource =>
          resource.run()
        })

        count should be(1)

        actual should be(expected)

      }
    }

  }

}
