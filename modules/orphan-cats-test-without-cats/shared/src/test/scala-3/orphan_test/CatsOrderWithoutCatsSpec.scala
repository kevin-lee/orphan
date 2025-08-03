package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyNum, MyOrder}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsOrderWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyOrder.compare(A, A)", testMyOrderCompare),
    example("test cats.Order", testCatsOrder),
  )

  def testMyOrderCompare: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = n1.compare(n2)
    val actual   = MyOrder[MyNum].compare(myNum1, myNum2)
    actual ==== expected
  }

  def testCatsOrder: Result = {
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = orphan_test.ExpectedMessages.ExpectedMessageForCatsOrder

    val actual = typeCheckErrors(
      """
        val _ = orphan_instance.OrphanCatsKernelInstances.MyNum.catsOrder
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    Result
      .assert(actualErrorMessage.startsWith(expectedMessage))
      .log("The actual error message doesn't start with the expected one.")

  }

}
