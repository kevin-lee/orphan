package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanSprayJsonMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonWriterWithoutSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceEncoder", testCirceEncoder)
  )

  def testCirceEncoder: Result = {
    val expected = s"""error: ${OrphanSprayJsonMessages.MissingSprayJsonJsonWriter}
                      |orphan_instance.OrphanSprayJsonInstances.MyBox.sprayJsonJsonWriter
                      |                                               ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanSprayJsonInstances.MyBox.sprayJsonJsonWriter"
    )
    actual ==== expected
  }

}
