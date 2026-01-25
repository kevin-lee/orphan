package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanSprayJsonMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonReaderWithoutSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    example("test JsonReader", testJsonReader)
  )

  def testJsonReader: Result = {
    val expected = s"""error: ${OrphanSprayJsonMessages.MissingSprayJsonJsonReader}
                      |orphan_instance.OrphanSprayJsonInstances.MyBox.sprayJsonJsonReader
                      |                                               ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanSprayJsonInstances.MyBox.sprayJsonJsonReader"
    )
    actual ==== expected
  }

}
