package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanSprayJsonMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonReaderWithoutSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceDecoder", testCirceDecoder)
  )

  def testCirceDecoder: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = OrphanSprayJsonMessages.MissingSprayJsonJsonReader

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanSprayJsonInstances.MyBox.sprayJsonJsonReader
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
