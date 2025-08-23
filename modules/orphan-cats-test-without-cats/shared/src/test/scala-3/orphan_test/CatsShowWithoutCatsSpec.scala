package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyShow}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsShowWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyShow.show(A)", testMyShowShow),
    example("test cats.Show", testCatsShow),
  )

  given intMyShow: MyShow[Int] = _.toString

  def testMyShowShow: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myNum <- Gen.constant(MyBox(n)).log("myNum")
  } yield {
    val expected = s"MyBox(a=${n.toString})"
    val actual   = MyShow[MyBox[Int]].show(myNum)
    actual ==== expected
  }

  def testCatsShow: Result = {
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = orphan.ExpectedMessages.ExpectedMessageForCatsShow

    val actual = typeCheckErrors(
      """
        val _ = orphan_instance.OrphanCatsInstances.MyBox.catsShow
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    Result
      .assert(actualErrorMessage.startsWith(expectedMessage))
      .log("The actual error message doesn't start with the expected one.")

  }

}
