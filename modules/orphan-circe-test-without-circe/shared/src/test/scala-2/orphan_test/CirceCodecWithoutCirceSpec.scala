package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanCirceMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceCodecWithoutCirceSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceCodec", testCirceCodec)
  )

  def testCirceCodec: Result = {
    val expected = s"""error: ${OrphanCirceMessages.MissingCirceCodec}
                      |orphan_instance.OrphanCirceInstances.MyBoxForCodec.circeCodec
                      |                                                   ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCirceInstances.MyBoxForCodec.circeCodec"
    )
    actual ==== expected
  }

}
