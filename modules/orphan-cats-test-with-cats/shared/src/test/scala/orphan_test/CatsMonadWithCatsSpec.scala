package orphan_test

import cats.Monad
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyMonad}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsMonadWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyMonad.pure", testMyMonadPure),
    property("test MyMonad.flatMap", testMyMonadFlatMap),
    property("test cats.Monad.map", testCatsMonadMap),
    property("test cats.Monad.pure", testCatsMonadPure),
    property("test cats.Monad.ap", testCatsMonadAp),
    property("test cats.Monad.flatMap", testCatsMonadFlatMap),
  )

  def testMyMonadPure: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
  } yield {
    val expected = MyBox(n)
    val actual   = MyMonad[MyBox].pure(n)
    actual ==== expected
  }

  def testMyMonadFlatMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val f: Int => MyBox[String] = a => MyBox((a * 2).toString)

    val expected = MyBox((n * 2).toString)
    val actual   = MyMonad[MyBox].flatMap(myBox)(f)
    actual ==== expected
  }

  def testCatsMonadMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox(n * 2)
    val actual   = Monad[MyBox].map(myBox)(_ * 2)
    actual ==== expected
  }

  def testCatsMonadPure: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
  } yield {
    val expected = MyBox(n)
    val actual   = Monad[MyBox].pure(n)
    actual ==== expected
  }

  def testCatsMonadAp: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    f     <- Gen.constant((a: Int) => a * 2).log("f")
    myBox <- Gen.constant(MyBox(n)).log("myoBox")

    myBoxWithFunction <- Gen.constant(MyBox(f)).log("myBoxWithFunction")
  } yield {
    val expected = MyBox(f(n))
    val actual   = Monad[MyBox].ap(myBoxWithFunction)(myBox)
    actual ==== expected
  }

  def testCatsMonadFlatMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val f: Int => MyBox[String] = a => MyBox((a * 2).toString)

    val expected = MyBox((n * 2).toString)
    val actual   = Monad[MyBox].flatMap(myBox)(f)
    actual ==== expected
  }

}
