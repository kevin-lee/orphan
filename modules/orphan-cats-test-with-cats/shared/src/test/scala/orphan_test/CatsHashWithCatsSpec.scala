package orphan_test

import cats.Hash
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyHash, MyNum}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsHashWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("MyHash.hash(A) should return hash", testMyHashHash),
    property("MyHash.eqv(A, A) should return true", testMyHashEqvTrue),
    property("MyHash.eqv(A, A) should return false", testMyHashEqvFalse),
    property("cats.Hash.hash(A) should return hash", testCatsHashHash),
    property("cats.Hash.eqv(A, A) should return true", testCatsHashEqvTrue),
    property("cats.Hash.eqv(A, A) should return false", testCatsHashEqvFalse),
  )

  def testMyHashHash: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myNum <- Gen.constant(MyNum(n)).log("myNum")
  } yield {
    val expected = myNum.##
    val actual   = MyHash[MyNum].hash(myNum)
    actual ==== expected
  }

  def testMyHashEqvTrue: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n1)).log("myNum2")
  } yield {
    val expected = true
    val actual   = MyHash[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testMyHashEqvFalse: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).map(n2 => if (n1 == n2) n2 + 1 else n2).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = false
    val actual   = MyHash[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

  def testCatsHashHash: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myNum <- Gen.constant(MyNum(n)).log("myNum")
  } yield {
    val expected = myNum.##
    val actual   = Hash[MyNum].hash(myNum)
    actual ==== expected
  }

  def testCatsHashEqvTrue: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n1)).log("myNum2")
  } yield {
    val expected = true
    val actual   = Hash[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testCatsHashEqvFalse: Property = for {
    n1     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2     <- Gen.int(Range.linear(0, Int.MaxValue)).map(n2 => if (n1 == n2) n2 + 1 else n2).log("n2")
    myNum1 <- Gen.constant(MyNum(n1)).log("myNum1")
    myNum2 <- Gen.constant(MyNum(n2)).log("myNum2")
  } yield {
    val expected = false
    val actual   = Hash[MyNum].eqv(myNum1, myNum2)
    actual ==== expected
  }

}
