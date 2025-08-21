package orphan_test

import cats.Invariant
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyInvariant}

/** @author Kevin Lee
  * @since 2025-08-21
  */
object CatsInvariantWithCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyInvariant.imap", testMyInvariantImap),
    property("test CatsInvariant.imap", testCatsInvariantImap),
  )

  def testMyInvariantImap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox((n + 999).toString)
    val actual   = MyInvariant[MyBox].imap(myBox)(a => (a + 999).toString)(_.toInt - 999)
    actual ==== expected
  }

  def testCatsInvariantImap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox((n + 999).toString)
    val actual   = Invariant[MyBox].imap(myBox)(a => (a + 999).toString)(_.toInt - 999)
    actual ==== expected
  }

}
