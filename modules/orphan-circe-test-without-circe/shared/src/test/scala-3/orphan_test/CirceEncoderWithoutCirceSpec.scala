package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanCirceMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceEncoderWithoutCirceSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceEncoder", testCirceEncoder)
  )

  def testCirceEncoder: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = OrphanCirceMessages.MissingCirceEncoder

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCirceInstances.MyBox.circeEncoder
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
