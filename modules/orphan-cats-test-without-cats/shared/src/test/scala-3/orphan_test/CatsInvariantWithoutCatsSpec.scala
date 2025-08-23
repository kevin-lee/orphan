package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanCatsInstances.{MyBox, MyInvariant}

/** @author Kevin Lee
  * @since 2025-08-21
  */
object CatsInvariantWithoutCatsSpec extends Properties {

  override def tests: List[Test] = List(
    property("test MyInvariant.imap", testMyInvariantMap),
    example("test CatsInvariant", testCatsInvariant),
  )

  def testMyInvariantMap: Property = for {
    n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
    myBox <- Gen.constant(MyBox(n)).log("myBox")
  } yield {
    val expected = MyBox((n + 999).toString)
    val actual   = MyInvariant[MyBox].imap(myBox)(a => (a + 999).toString)(_.toInt - 999)
    actual ==== expected
  }

  def testCatsInvariant: Result = {
    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = orphan.OrphanCatsMessages.MisingCatsInvariant

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCatsInstances.MyBox.catsInvariant
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
