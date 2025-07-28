package orphan_test

import cats.Functor
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyFunctor}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsFunctorSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyFunctor", testMyFunctor),
    property("test CatsFunctor", testCatsFunctor),
  )

  def testMyFunctor: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = MyFunctor[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testCatsFunctor: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = Functor[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

}
