package orphan

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2026-01-25
  */
object OrphanSprayJsonMessagesSpec extends Properties {

  private val ExpectedDependency = """"io.spray" %%  "spray-json" % SPRAY_JSON_VERSION"""

  override def tests: List[Test] = List(
    example(
      "OrphanSprayJsonMessages.MissingSprayJsonJsonWriter should contain expected strings",
      testMissingSprayJsonJsonWriter
    ),
    example(
      "OrphanSprayJsonMessages.MissingSprayJsonJsonReader should contain expected strings",
      testMissingSprayJsonJsonReader
    ),
    example(
      "OrphanSprayJsonMessages.MissingSprayJsonJsonFormat should contain expected strings",
      testMissingSprayJsonJsonFormat
    ),
  )

  def testMissingSprayJsonJsonWriter: Result = {
    val message = OrphanSprayJsonMessages.MissingSprayJsonJsonWriter
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "SprayJsonJsonWriter")(_.contains(_)),
        Result.diffNamed(
          "Should contain spray-json type",
          message,
          "If you want to have an instance of spray.json.JsonWriter[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testMissingSprayJsonJsonReader: Result = {
    val message = OrphanSprayJsonMessages.MissingSprayJsonJsonReader
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "SprayJsonJsonReader")(_.contains(_)),
        Result.diffNamed(
          "Should contain spray-json type",
          message,
          "If you want to have an instance of spray.json.JsonReader[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testMissingSprayJsonJsonFormat: Result = {
    val message = OrphanSprayJsonMessages.MissingSprayJsonJsonFormat
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "SprayJsonJsonFormat")(_.contains(_)),
        Result.diffNamed(
          "Should contain spray-json type",
          message,
          "If you want to have an instance of spray.json.JsonFormat[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }
}
