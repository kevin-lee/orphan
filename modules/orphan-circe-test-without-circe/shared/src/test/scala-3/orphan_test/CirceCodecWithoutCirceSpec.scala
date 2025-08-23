package orphan_test

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceCodecWithoutCirceSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceCodec", testCirceCodec)
  )

  def testCirceCodec: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = OrphanCirceMessages.MissingCirceCodec

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanCirceInstances.MyBoxForCodec.circeCodec
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    Result
      .assert(actualErrorMessage.startsWith(expectedMessage))
      .log("The actual compile-time error doesn't start with the expected error message.")
  }

}
