package orphan_test

import cats.Show
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{Another, MyBox, MyShow, Something}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsShowWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyShow.show(A)", testMyShowShow),
    property("test cats.Show.show(A)", testCatsShowShow),
    property("test cats.Show.show(A) 2", testCatsShowShow2),
  )

  implicit def intMyShow: MyShow[Int] = _.toString

  def testMyShowShow: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myNum <- Gen.constant(MyBox(n)).log("myNum")
  } yield {
    val expected = s"MyBox(a=${n.toString})"
    val actual   = MyShow[MyBox[Int]].show(myNum)
    actual ==== expected
  }

  def testCatsShowShow: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myNum <- Gen.constant(MyBox(n)).log("myNum")
  } yield {
    val expected = s"MyBox(a=${n.toString})"
    val actual   = Show[MyBox[Int]].show(myNum)
    actual ==== expected
  }

  def testCatsShowShow2: Property = for {
    n         <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    something <- Gen.constant(Something(Another(n))).log("something")
  } yield {
    val expected = s"Something(a=Another(n=${n.toString}))"
    val actual   = Show[Something[Another]].show(something)

    actual ==== expected
  }

}
