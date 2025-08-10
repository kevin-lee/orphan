package orphan_test

import hedgehog.*
import hedgehog.runner.*
import extras.testing.CompileTimeError
import orphan_instance.OrphanCatsInstances.{MyBox, MyMonad}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsMonadWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyMonad.pure", testMyMonadPure),
    property("test MyMonad.flatMap", testMyMonadFlatMap),
    example("test CatsMonad", testCatsMonad),
  )

  def testMyMonadPure: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
  } yield {
    val expected = MyBox(n)
    val actual   = MyMonad[MyBox].pure(n)
    actual ==== expected
  }

  def testMyMonadFlatMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val f: Int => MyBox[String] = a => MyBox((a * 2).toString)

    val expected = MyBox((n * 2).toString)
    val actual   = MyMonad[MyBox].flatMap(myBox)(f)
    actual ==== expected
  }

  def testCatsMonad: Result = {
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCatsMonad}
                      |orphan_instance.OrphanCatsInstances.MyBox.catsMonad
                      |                                          ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsInstances.MyBox.catsMonad"
    )
    actual ==== expected
  }

}
