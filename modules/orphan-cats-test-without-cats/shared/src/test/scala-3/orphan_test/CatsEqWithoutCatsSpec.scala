package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsKernelInstances.{MyEq, MyNum}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsEqWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("MyEq.eqv(A, A) should return true", testMyEqEqvTrue),
    property("MyEq.eqv(A, A) should return false", testMyEqEqvFalse),
    example("test cats.Eq", testCatsEq),
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

  def testCatsEq: Result = {
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = orphan.OrphanCatsMessages.MisingCatsEq

    val actual = typeCheckErrors(
      """
        val _ = orphan_instance.OrphanCatsKernelInstances.MyNum.catsEq
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    Result
      .assert(actualErrorMessage.startsWith(expectedMessage))
      .log("The actual error message doesn't start with the expected one.")

  }

}
