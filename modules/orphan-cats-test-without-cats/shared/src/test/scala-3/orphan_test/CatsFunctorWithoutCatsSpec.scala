package orphan_test

import hedgehog.*
import hedgehog.runner.*
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
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = orphan.OrphanCatsMessages.MisingCatsFunctor

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCatsInstances.MyBox.catsFunctor
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
