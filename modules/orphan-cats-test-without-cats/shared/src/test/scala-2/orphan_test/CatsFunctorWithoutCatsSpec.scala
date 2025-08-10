package orphan_test

import hedgehog.*
import hedgehog.runner.*
import extras.testing.CompileTimeError
import orphan_instance.OrphanCatsInstances.{MyBox, MyFunctor}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsFunctorWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyFunctor.map", testMyFunctorMap),
    example("test CatsFunctor", testCatsFunctor),
  )

  def testMyFunctorMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = MyFunctor[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testCatsFunctor: Result = {
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCatsFunctor}
                      |orphan_instance.OrphanCatsInstances.MyBox.catsFunctor
                      |                                          ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsInstances.MyBox.catsFunctor"
    )
    actual ==== expected
  }

}
