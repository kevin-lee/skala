package io.kevinlee.skala.util

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.util.{Failure, Success, Try}

import TryWith.SideEffect.tryWith

/**
  * @author Kevin Lee
  * @since 2016-05-08
  */
class TryWithSpec extends WordSpec with Matchers with MockFactory {

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
          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).once()
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
          resource.run _ expects() returning expected once()

          resource.close _ expects() once()
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

          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).twice()
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
          resource.run _ expects() once()
          anotherResource.run _ expects() returning expected once()

          anotherResource.close _ expects() once()
          resource.close _ expects() once()
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
        actual should be(expected)
      }
    }

    "tryWith(SomeResource) { someResource => someResource.run() // throwing TryTestException }" should {

      s"call close SomeResource after run block and throw ${TryTestException.getClass.getSimpleName}" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _) expects() throws TryTestException

          (resource.close _).expects().returning(()).once()
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
          (resource.run _) expects() throwing (TryTestException) once() //throws TryTestException

          (resource.close _).expects().returning(()).once()
        }

        a[TryTestException.type] should be thrownBy {
          tryWith(resource) { someResource =>
            tryWith(new AutoCloseable {
              override def close(): Unit = ()
            }) { anotherResource =>
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
          (resource.run _) expects() throws TryTestException

          (resource.close _).expects().returning(()).once()
        }
        val resource2 = mock[SomeResource[Unit]]

        inSequence {
          (resource2.run _) expects() never()

          (resource2.close _).expects().returning(()).never()
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

        var logCount = 0
        var exceptionThrown: Option[Throwable] = None

        val actual = tryWith(CountableCloseable()) { resource =>
          resource.run()
        } { x =>
          exceptionThrown = Some(x)
          logCount += 1
        }

        count should be (1)
        logCount should be (1)

        actual should be (expected)

        exceptionThrown shouldBe defined

        exceptionThrown should contain (expectedException)

        exceptionThrown match {
          case Some(ex) =>
            ex shouldBe a [RuntimeException]
            ex should be theSameInstanceAs expectedException
          case _ =>
            fail(s"$expectedException was not thrown.")
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
          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).once()
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
          resource.run _ expects() returning expectedString once()

          resource.close _ expects() once()
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

          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).twice()
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
          anotherResource.run _ expects() onCall { _ => resource.run() } once()
          resource.run _ expects() returning expectedString once()

          anotherResource.close _ expects() onCall { _ => resource.close() } once()
          resource.close _ expects() twice()
        }

        val actual = Try(tryWith(resource) { someResource =>
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
          anotherResource.run _ expects() onCall { _ => resource.run() } once()
          resource.run _ expects() returning expectedString once()

          anotherResource.close _ expects() once()
          resource.close _ expects() once()
        }

        val actual = Try(tryWith(resource) { someResource =>
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
          (resource.run _) expects() throws TryTestException

          (resource.close _).expects().returning(()).once()
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
          (resource.run _) expects() throwing (TryTestException) once() //throws TryTestException

          (resource.close _).expects().returning(()).once()
        }

        val actual = Try(tryWith(resource) { someResource =>
          tryWith(new AutoCloseable {
            override def close(): Unit = ()
          }) { anotherResource =>
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
          (resource.run _) expects() throws TryTestException

          (resource.close _).expects().returning(()).once()
        }
        val resource2 = mock[SomeResource[Unit]]

        inSequence {
          (resource2.run _) expects() never()

          (resource2.close _).expects().returning(()).never()
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

  "TryWith.apply()" when {

    "TryWith(resource which is null) { // run block }" should {

      "return Failure(NPE)" in {

        val resource: SomeResource[Nothing] = null

        val actual = TryWith(resource) { someResource =>
          someResource.run()
        }

        actual match {
          case expected@Failure(npe: NullPointerException) =>
          case _ => fail(s"$actual is not ${classOf[NullPointerException]}")
        }
      }
    }

    "TryWith(SomeResource) { // run block }" should {

      val expected = Success(())
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).once()
        }

        val actual = TryWith(resource) { someResource =>
          someResource.run()
        }

        actual should be(expected)

      }
    }

    "val actual = TryWith(SomeResource) { // run block }" should {

      val expectedString = "Hello"
      val expected = Success(expectedString)

      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[String]]

        inSequence {
          resource.run _ expects() returning expectedString once()

          resource.close _ expects() once()
        }
        val actual = TryWith(resource) { someResource =>
          someResource.run()
        }
        actual should be(expected)
      }
    }

    """:
      |    TryWith(SomeResource) {
      |      tryWith(AnotherResource) {
      |        // run block
      |      }
      |    }""".stripMargin should {

      val expected = Success(())
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {

          (resource.run _).expects().returning(()).once()

          (resource.close _).expects().returning(()).twice()
        }

        val actual = TryWith(resource) { someResource =>
          tryWith(new AnotherResource[Unit](someResource)) { anotherSource =>
            anotherSource.run()
          }
        }

        actual should be(expected)

      }
    }

    """val actual = TryWith(SomeResource) {
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
          anotherResource.run _ expects() onCall { _ => resource.run() } once()
          resource.run _ expects() returning expectedString once()

          anotherResource.close _ expects() onCall { _ => resource.close() } once()
          resource.close _ expects() twice()
        }

        val actual = TryWith(resource) { someResource =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        actual should be(expected)
      }
    }

    """val actual = TryWith(resource) { someResource =>
      |                      tryWith(anotherResource) { someOtherResource =>
      |                        someOtherResource.run()
      |                      }
      |                    }""".stripMargin should {

      val expectedString = "Hello"
      val expected = Success(expectedString)

      s"call close anotherResource and close resource after run block then return $expected" in {

        val resource = mock[SomeResource[String]]
        val anotherResource = mock[SomeResource[String]]

        inSequence {
          anotherResource.run _ expects() onCall { _ => resource.run() } once()
          resource.run _ expects() returning expectedString once()

          anotherResource.close _ expects() once()
          resource.close _ expects() once()
        }

        val actual = TryWith(resource) { someResource =>
          tryWith(anotherResource) { someOtherResource =>
            someOtherResource.run()
          }
        }
        actual should be(expected)
      }
    }


    "TryWith(SomeResource) { someResource => someResource.run() // throwing TryTestException }" should {

      val expected = Failure(TryTestException)
      s"call close SomeResource after run block and return $expected" in {


        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _) expects() throws TryTestException

          (resource.close _).expects().returning(()).once()
        }

        val actual = TryWith(resource) { someResource =>
          someResource.run()
        }

        actual should be(expected)

      }
    }

    """TryWith(SomeResource) { someResource =>
      |         tryWith(new AutoCloseable {
      |           override def close(): Unit = ()
      |         }) { anotherResource =>
      |           someResource.run() // throws TryTestException here
      |         }
      |         throw AnotherTryTestException
      |       }""".stripMargin should {

      val expected = Failure(TryTestException)
      s"call close SomeResource after run block and return $expected" in {

        val resource = mock[SomeResource[Unit]]

        inSequence {
          (resource.run _) expects() throwing (TryTestException) once() //throws TryTestException

          (resource.close _).expects().returning(()).once()
        }

        val actual = TryWith(resource) { someResource =>
          tryWith(new AutoCloseable {
            override def close(): Unit = ()
          }) { anotherResource =>
            someResource.run()
          }
          throw AnotherTryTestException
        }

        actual should be(expected)
      }
    }

    """TryWith(resource) { someResource =>
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
          (resource.run _) expects() throws TryTestException

          (resource.close _).expects().returning(()).once()
        }
        val resource2 = mock[SomeResource[Unit]]

        inSequence {
          (resource2.run _) expects() never()

          (resource2.close _).expects().returning(()).never()
        }

        val actual = TryWith(resource) { someResource =>
          someResource.run()

          tryWith(resource2) { anotherResource =>
            anotherResource.run()
          }
        }

        actual should be(expected)
      }
    }

    "TryWith(an instantiation of a resource)" should {
      val expected = Success(())
      s"instantiate it once and use the same one and return $expected" in {

        var count = 0

        case class CountableCloseable() extends AutoCloseable {
          count += 1

          def run(): Unit = ()

          def close(): Unit = ()
        }

        val actual = TryWith(new CountableCloseable()) { resource =>
          resource.run()
        }

        count should be(1)

        actual should be(expected)

      }
    }

    "TryWith(an instantiation of a resource that throws a RuntimException when close is called){ // block }(logger)" should {
      val expected = Success(999)
      s"instantiate it once and use the same one and return $expected and the exception when closing should be handled by the logger" in {

        var count = 0
        val expectedException = new RuntimeException()

        case class CountableCloseable() extends AutoCloseable {
          count += 1

          def run(): Int = 999

          def close(): Unit = throw expectedException
        }

        var logCount = 0
        var exceptionThrown: Option[Throwable] = None

        val actual = TryWith(CountableCloseable()) { resource =>
          resource.run()
        } { x =>
          exceptionThrown = Some(x)
          logCount += 1
        }

        count should be (1)
        logCount should be (1)

        actual should be (expected)

        exceptionThrown shouldBe defined

        exceptionThrown should contain (expectedException)

        exceptionThrown match {
          case Some(ex) =>
            ex shouldBe a [RuntimeException]
            ex should be theSameInstanceAs expectedException
          case _ =>
            fail(s"$expectedException was not thrown.")
        }

      }
    }

  }

}
