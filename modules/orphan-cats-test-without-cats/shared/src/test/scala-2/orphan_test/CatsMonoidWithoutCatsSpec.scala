package orphan_test

import hedgehog.*
import hedgehog.runner.*
import extras.testing.CompileTimeError
import orphan_instance.OrphanCatsKernelInstances.{MyMonoid, MyNum}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsMonoidWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    example("test MyMonoid.empty", testMyMonoidEmpty),
    property("test MyMonoid.combine", testMyMonoidCombine),
    example("test CatsMonoid", testCatsMonoid),
  )

  def testMyMonoidEmpty: Result = {
    val expected = MyNum(0)
    val actual   = MyMonoid[MyNum].empty

    actual ==== expected
  }

  def testMyMonoidCombine: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = MyNum(n1 + n2)
    val actual   = MyMonoid[MyNum].combine(myNum1, myNum2)
    actual ==== expected
  }

  def testCatsMonoid: Result = {
    val expected = s"""error: ${orphan.OrphanCatsMessages.MissingCatsMonoid}
                      |orphan_instance.OrphanCatsKernelInstances.MyNum.catsMonoid
                      |                                                ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsKernelInstances.MyNum.catsMonoid"
    )
    actual ==== expected
  }

}
