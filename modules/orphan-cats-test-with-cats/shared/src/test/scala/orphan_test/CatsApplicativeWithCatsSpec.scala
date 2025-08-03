package orphan_test

import cats.Applicative
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyApplicative, MyBox}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsApplicativeWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyApplicative.pure", testMyApplicativePure),
    property("test MyApplicative.map", testMyApplicativeMap),
    property("test MyApplicative.ap", testMyApplicativeAp),
    property("test cats.Applicative.pure", testCatsApplicativePure),
    property("test cats.Applicative.map", testCatsApplicativeMap),
    property("test cats.Applicative.ap", testCatsApplicativeAp),
  )

  def testMyApplicativePure: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
  } yield {
    val expected = MyBox(n)
    val actual   = MyApplicative[MyBox].pure(n)
    actual ==== expected
  }

  def testMyApplicativeMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = MyApplicative[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testMyApplicativeAp: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    f     <- Gen.constant((a: Int) => a * 2).log("f")
    myBox <- Gen.constant(MyBox(n)).log("myoBox")

    myBoxWithFunction <- Gen.constant(MyBox(f)).log("myBoxWithFunction")
  } yield {
    val expected = MyBox(f(n))
    val actual   = MyApplicative[MyBox].ap(myBoxWithFunction)(myBox)
    actual ==== expected
  }

  def testCatsApplicativePure: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
  } yield {
    val expected = MyBox(n)
    val actual   = Applicative[MyBox].pure(n)
    actual ==== expected
  }

  def testCatsApplicativeMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = Applicative[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testCatsApplicativeAp: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    f     <- Gen.constant((a: Int) => a * 2).log("f")
    myBox <- Gen.constant(MyBox(n)).log("myoBox")

    myBoxWithFunction <- Gen.constant(MyBox(f)).log("myBoxWithFunction")
  } yield {
    val expected = MyBox(f(n))
    val actual   = Applicative[MyBox].ap(myBoxWithFunction)(myBox)
    actual ==== expected
  }

}
