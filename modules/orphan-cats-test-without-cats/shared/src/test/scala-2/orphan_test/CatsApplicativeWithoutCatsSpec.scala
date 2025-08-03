package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan.testing.CompileTimeError
import orphan_instance.OrphanCatsInstances.{MyApplicative, MyBox}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsApplicativeWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyApplicative.pure", testMyApplicativePure),
    property("test MyApplicative.map", testMyApplicativeMap),
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

  def testMyApplicativeMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = MyApplicative[MyBox].map(myBox)(_ * 2)
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
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCatsApplicative}
                      |orphan_instance.OrphanCatsInstances.MyBox.catsApplicative
                      |                                          ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsInstances.MyBox.catsApplicative"
    )
    actual ==== expected
  }

}
