package orphan_test

import cats.Monoid
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyMonoid, MyNum}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsMonoidWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    example("test MyMonoid.empty", testMyMonoidEmpty),
    property("test MyMonoid.combine", testMyMonoidCombine),
    example("test CatsMonoid.empty", testCatsMonoidEmpty),
    property("test CatsMonoid.combine", testCatsMonoidCombine),
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

  def testCatsMonoidEmpty: Result = {
    val expected = MyNum(0)
    val actual   = Monoid[MyNum].empty

    actual ==== expected
  }

  def testCatsMonoidCombine: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = MyNum(n1 + n2)
    val actual   = Monoid[MyNum].combine(myNum1, myNum2)
    actual ==== expected
  }

}
