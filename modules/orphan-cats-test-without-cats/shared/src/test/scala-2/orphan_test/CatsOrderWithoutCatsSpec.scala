package orphan_test

import hedgehog.*
import hedgehog.runner.*
import extras.testing.CompileTimeError
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
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCatsOrder}
                      |orphan_instance.OrphanCatsKernelInstances.MyNum.catsOrder
                      |                                                ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsKernelInstances.MyNum.catsOrder"
    )
    actual ==== expected
  }

}
