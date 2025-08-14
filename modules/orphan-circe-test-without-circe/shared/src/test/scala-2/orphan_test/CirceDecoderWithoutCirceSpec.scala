package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceDecoderWithoutCirceSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceDecoder", testCirceDecoder)
  )

  def testCirceDecoder: Result = {
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCirceDecoder}
                      |orphan_instance.OrphanCirceInstances.MyBox.circeDecoder
                      |                                           ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCirceInstances.MyBox.circeDecoder"
    )
    actual ==== expected
  }

}
