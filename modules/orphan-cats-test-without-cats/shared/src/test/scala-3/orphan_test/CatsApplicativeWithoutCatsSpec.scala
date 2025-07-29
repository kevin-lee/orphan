package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyApplicative, MyBox}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsApplicativeWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyApplicative.pure", testMyApplicativePure),
    property("test MyApplicative.ap", testMyApplicativeAp),
    example("test cats.Applicative", testCatsApplicative),
  )

  def testMyApplicativePure: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
  } yield {
    val expected = MyBox(n)
    val actual   = MyApplicative[MyBox].pure(n)
    actual ==== expected
  }

  def testMyApplicativeAp: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    f     <- Gen.constant((a: Int) => a * 2).log("f")
    myBox <- Gen.constant(MyBox(n)).log("myoBox")

    myBoxWithFunction <- Gen.constant(MyBox(f)).log("myBoxWithFunction")
  } yield {
    val expected = MyBox(f(n))
    val actual   = MyApplicative[MyBox].ap(myBoxWithFunction)(myBox)
    actual ==== expected
  }

  def testCatsApplicative: Result = {
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = ExpectedMessages.ExpectedMessageForCatsApplicative

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCatsInstances.MyBox.catsApplicative
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
