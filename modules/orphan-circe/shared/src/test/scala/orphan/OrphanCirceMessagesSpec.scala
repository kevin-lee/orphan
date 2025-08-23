package orphan

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2025-08-23
  */
object OrphanCirceMessagesSpec extends Properties {
  private val ExpectedDependency = "\"io.circe\" %% \"circe-core\" % CIRCE_VERSION"

  override def tests: List[Test] = List(
    example(
      "OrphanCirceMessages.MissingCirceEncoder should contain expected strings",
      testMissingCirceEncoder
    ),
    example(
      "OrphanCirceMessages.MissingCirceDecoder should contain expected strings",
      testMissingCirceDecoder
    ),
    example(
      "OrphanCirceMessages.MissingCirceCodec should contain expected strings",
      testMissingCirceCodec
    ),
  )

  def testMissingCirceEncoder: Result = {
    val message = OrphanCirceMessages.MissingCirceEncoder
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CirceEncoder")(_.contains(_)),
        Result.diffNamed(
          "Should contain circe type",
          message,
          "If you want to have an instance of io.circe.Encoder[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testMissingCirceDecoder: Result = {
    val message = OrphanCirceMessages.MissingCirceDecoder
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CirceDecoder")(_.contains(_)),
        Result.diffNamed(
          "Should contain circe type",
          message,
          "If you want to have an instance of io.circe.Decoder[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testMissingCirceCodec: Result = {
    val message = OrphanCirceMessages.MissingCirceCodec
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CirceCodec")(_.contains(_)),
        Result.diffNamed(
          "Should contain circe type",
          message,
          "If you want to have an instance of io.circe.Codec[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }
}
