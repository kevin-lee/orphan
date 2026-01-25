package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanSprayJsonMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonFormatWithoutSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceCodec", testCirceCodec)
  )

  def testCirceCodec: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = OrphanSprayJsonMessages.MissingSprayJsonJsonFormat

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanSprayJsonInstances.MyBoxForJsonFormat.sprayJsonJsonFormat
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    Result
      .assert(actualErrorMessage.startsWith(expectedMessage))
      .log("The actual compile-time error doesn't start with the expected error message.")
  }

}
