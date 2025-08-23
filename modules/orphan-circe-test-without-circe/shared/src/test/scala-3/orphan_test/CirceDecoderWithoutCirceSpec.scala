package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanCirceMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceDecoderWithoutCirceSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceDecoder", testCirceDecoder)
  )

  def testCirceDecoder: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = OrphanCirceMessages.MissingCirceDecoder

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCirceInstances.MyBox.circeDecoder
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
