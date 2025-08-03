package orphan_test

import cats.kernel.Order
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyNum, MyOrder}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsOrderWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyOrder.compare(A, A)", testMyOrderCompare),
    property("test cats.Order.compare(A, A)", testCatsOrderCompare),
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

  def testCatsOrderCompare: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = n1.compare(n2)
    val actual   = Order[MyNum].compare(myNum1, myNum2)
    actual ==== expected
  }

}
