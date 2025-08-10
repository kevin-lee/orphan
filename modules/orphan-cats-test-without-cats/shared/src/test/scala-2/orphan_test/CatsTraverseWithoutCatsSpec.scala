package orphan_test

import hedgehog.*
import hedgehog.runner.*
import extras.testing.CompileTimeError
import orphan_instance.OrphanCatsInstances.{MyApplicative, MyBox, MyEval, MyTraverse}

/** @author Kevin Lee
  * @since 2025-07-28
  */
object CatsTraverseWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyTraverse.traverse", testMyTraverseTraverse),
    property("test MyTraverse.foldLeft", testMyTraverseFoldLeft),
    property("test MyTraverse.foldRight", testMyTraverseFoldRight),
    example("test cats.Traverse", testCatsTraverse),
  )

  implicit def optionApplicative: MyApplicative[Option] = new MyApplicative[Option] {
    override def pure[A](a: A): Option[A] = Some(a)

    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = fa.flatMap(a => ff.map(_(a)))
  }

  def testMyTraverseTraverse: Property = for {
    n <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    f <- Gen
           .constant(if (n > 300) (a: Int) => Some(a * 2) else (_: Int) => None)
           .log("f: Int => Option[Int] where it's Some(Int) if the given Int > 300. Otherwise it's None.")
  } yield {
    val input = MyBox(n)

    val expected = Some(MyBox(n * 2))
    val actual   = MyTraverse[MyBox].traverse(input)(f)

    actual ==== expected
  }

  def testMyTraverseFoldLeft: Property = for {
    n1    <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2    <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myBox <- Gen.constant(MyBox(n1)).log("myBox")
  } yield {
    val expected = n2 - n1
    val actual   = MyTraverse[MyBox].foldLeft(myBox, n2)((b, a) => b - a)
    actual ==== expected
  }

  def testMyTraverseFoldRight: Property = for {
    n1    <- Gen.int(Range.linear(0, Int.MaxValue)).log("n1")
    n2    <- Gen.int(Range.linear(0, Int.MaxValue)).log("n2")
    myBox <- Gen.constant(MyBox(n1)).log("myBox")
  } yield {
    val expected = n1 - n2
    val actual   = MyTraverse[MyBox].foldRight(myBox, MyEval(n2))((a, myEvalB) => MyEval(a - myEvalB.thunk()))
    actual.thunk() ==== expected
  }

  def testCatsTraverse: Result = {
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCatsTraverse}
                      |orphan_instance.OrphanCatsInstances.MyBox.catsTraverse
                      |                                          ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsInstances.MyBox.catsTraverse"
    )
    actual ==== expected
  }

}
