package orphan_test

import cats.Eq
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyEq, MyNum}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsEqWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("MyEq.eqv(A, A) should return true", testMyEqEqvTrue),
    property("MyEq.eqv(A, A) should return false", testMyEqEqvFalse),
    property("cats.Eq.eqv(A, A) should return true", testCatsEqEqvTrue),
    property("cats.Eq.eqv(A, A) should return false", testCatsEqEqvFalse),
  )

  def testMyEqEqvTrue: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n1)).log("myNum2")
  } yield {
    val expected = true
    val actual   = MyEq[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testMyEqEqvFalse: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).map(n2 => if (n1 == n2) n2 + 1 else n2).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = false
    val actual   = MyEq[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

  def testCatsEqEqvTrue: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n1)).log("myNum2")
  } yield {
    val expected = true
    val actual   = Eq[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testCatsEqEqvFalse: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).map(n2 => if (n1 == n2) n2 + 1 else n2).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = false
    val actual   = Eq[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

}
