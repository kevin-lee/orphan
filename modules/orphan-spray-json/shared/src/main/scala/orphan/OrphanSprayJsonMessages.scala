package orphan

/** @author Kevin Lee
  * @since 2026-01-22
  */
@SuppressWarnings(Array("org.wartremover.warts.FinalVal", "org.wartremover.warts.PublicInference"))
object OrphanSprayJsonMessages {
  // scalafix:off DisableSyntax.noFinalVal

  final val MissingSprayJsonJsonWriter =
    "Missing an instance of `SprayJsonJsonWriter` which means you're trying to use spray.json.JsonWriter, " +
      "but spray-json library is missing in your project config. " +
      "If you want to have an instance of spray.json.JsonWriter[A] provided, " +
      """please add `"io.spray" %%  "spray-json" % SPRAY_JSON_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingSprayJsonJsonReader =
    "Missing an instance of `SprayJsonJsonReader` which means you're trying to use spray.json.JsonReader, " +
      "but spray-json library is missing in your project config. " +
      "If you want to have an instance of spray.json.JsonReader[A] provided, " +
      """please add `"io.spray" %%  "spray-json" % SPRAY_JSON_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingSprayJsonJsonFormat =
    "Missing an instance of `SprayJsonJsonFormat` which means you're trying to use spray.json.JsonFormat, " +
      "but spray-json library is missing in your project config. " +
      "If you want to have an instance of spray.json.JsonFormat[A] provided, " +
      """please add `"io.spray" %%  "spray-json" % SPRAY_JSON_VERSION` to your libraryDependencies in build.sbt"""

  // scalafix:on
}
