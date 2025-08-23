package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyContravariant, MyEncoder, MyInvariant}

/** @author Kevin Lee
  * @since 2025-08-21
  */
object CatsContravariantWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyContravariant.imap", testMyContravariantImap),
    property("test MyContravariant.imap with Invariant", testMyContravariantImapWithInvariant),
    property("test MyContravariant.contramap", testMyContravariantContramap),
    example("test CatsContravariant", testCatsContravariant),
  )

  def testMyContravariantImap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val myEncoder = new MyEncoder[MyBox[Int]] {
      override def encode(value: MyBox[Int]): String = s"Result(a=${(value.a + 999).toString})"
    }

    val input    = myBox.copy(a = myBox.a.toString)
    val expected = s"Result(a=${n.toString})"
    val actual   =
      MyContravariant[MyEncoder].imap(myEncoder)(a => MyBox((a.a + 999).toString))(a => MyBox(a.a.toInt - 999))
    actual.encode(input) ==== expected
  }

  def testMyContravariantImapWithInvariant: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val myEncoder = new MyEncoder[MyBox[Int]] {
      override def encode(value: MyBox[Int]): String = s"Result(a=${(value.a + 999).toString})"
    }

    val input    = myBox.copy(a = myBox.a.toString)
    val expected = s"Result(a=${n.toString})"
    val actual   =
      MyInvariant[MyEncoder].imap(myEncoder)(a => MyBox((a.a + 999).toString))(a => MyBox(a.a.toInt - 999))
    actual.encode(input) ==== expected
  }

  def testMyContravariantContramap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val myEncoder = new MyEncoder[MyBox[Int]] {
      override def encode(value: MyBox[Int]): String = s"Result(a=${(value.a + 999).toString})"
    }

    val input    = myBox.copy(a = myBox.a.toString)
    val expected = s"Result(a=${n.toString})"
    val actual   = MyContravariant[MyEncoder].contramap(myEncoder)((a: MyBox[String]) => MyBox(a.a.toInt - 999))
    actual.encode(input) ==== expected
  }

  def testCatsContravariant: Result = {

    val expected = s"""error: ${orphan.OrphanCatsMessages.MisingCatsContravariant}
                      |orphan_instance.OrphanCatsInstances.MyEncoder.catsContravariant
                      |                                              ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCatsInstances.MyEncoder.catsContravariant"
    )
    actual ==== expected
  }

}
