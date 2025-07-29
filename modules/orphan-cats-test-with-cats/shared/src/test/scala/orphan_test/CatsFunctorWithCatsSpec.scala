package orphan_test

import cats.Functor
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyFunctor}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsFunctorWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyFunctor.map", testMyFunctorMap),
    property("test CatsFunctor.map", testCatsFunctorMap),
  )

  def testMyFunctorMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = MyFunctor[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testCatsFunctorMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = Functor[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

}
