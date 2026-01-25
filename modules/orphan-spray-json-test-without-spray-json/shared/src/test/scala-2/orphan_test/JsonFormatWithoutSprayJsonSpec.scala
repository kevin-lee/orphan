package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanSprayJsonMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonFormatWithoutSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    example("test JsonFormat", testJsonFormat)
  )

  def testJsonFormat: Result = {
    val expected = s"""error: ${OrphanSprayJsonMessages.MissingSprayJsonJsonFormat}
                      |orphan_instance.OrphanSprayJsonInstances.MyBoxForJsonFormat.sprayJsonJsonFormat
                      |                                                            ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanSprayJsonInstances.MyBoxForJsonFormat.sprayJsonJsonFormat"
    )
    actual ==== expected
  }

}
