package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan.OrphanSprayJsonMessages

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonWriterWithoutSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    example("testCirceEncoder", testCirceEncoder)
  )

  def testCirceEncoder: Result = {

    import scala.compiletime.testing.typeCheckErrors
    val expectedMessage = OrphanSprayJsonMessages.MissingSprayJsonJsonWriter

    val actual = typeCheckErrors(
      """
        val _ =  orphan_instance.OrphanSprayJsonInstances.MyBox.sprayJsonJsonWriter
      """
    )

    val actualErrorMessage = actual.map(_.message).mkString
    (actualErrorMessage ==== expectedMessage)
  }

}
