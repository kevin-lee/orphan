package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyNum, MySemigroup}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsSemigroupWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MySemigroup.combine", testMySemigroupCombine),
    example("test CatsSemigroup", testCatsSemigroup),
  )

  def testMySemigroupCombine: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = MyNum(n1 + n2)
    val actual   = MySemigroup[MyNum].combine(myNum1, myNum2)
    actual ==== expected
  }

  def testCatsSemigroup: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = orphan.OrphanCatsMessages.MissingCatsSemigroup

    val actual = typeCheckErrors(
      """
        val _ = orphan_instance.OrphanCatsKernelInstances.MyNum.catsSemigroup
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    Result
      .assert(actualErrorMessage.startsWith(expectedMessage))
      .log("The actual error message doesn't start with the expected one.")
  }

}
