package orphan_test

import extras.testing.CompileTimeError
import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceEncoderWithoutCirceSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceEncoder", testCirceEncoder)
  )

  def testCirceEncoder: Result = {
    val expected = s"""error: ${ExpectedMessages.ExpectedMessageForCirceEncoder}
                      |orphan_instance.OrphanCirceInstances.MyBox.circeEncoder
                      |                                           ^""".stripMargin

    val actual = CompileTimeError.from(
      "orphan_instance.OrphanCirceInstances.MyBox.circeEncoder"
    )
    actual ==== expected
  }

}
