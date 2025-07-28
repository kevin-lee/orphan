package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyFunctor}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsFunctorSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyFunctor", testMyFunctor),
    example("test CatsFunctor", testCatsFunctor),
  )

  def testMyFunctor: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = MyFunctor[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testCatsFunctor: Result = {
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage =
      """Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, but cats library is missing in your project config. If you want to have an instance of cats.Functor[F[*]] provided by extras, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCatsInstances.MyBox.catsFunctor
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
