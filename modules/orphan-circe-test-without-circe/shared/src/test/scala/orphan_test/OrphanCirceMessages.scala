package orphan_test

/** @author Kevin Lee
  * @since 2025-07-28
  */
object OrphanCirceMessages {

  val MissingCirceEncoder: String =
    """Missing an instance of `CirceEncoder` which means you're trying to use io.circe.Encoder, but circe library is missing in your project config. If you want to have an instance of io.circe.Encoder[A] provided, please add `"io.circe" %% "circe-core" % CIRCE_VERSION` to your libraryDependencies in build.sbt"""

  val MissingCirceDecoder: String =
    """Missing an instance of `CirceDecoder` which means you're trying to use io.circe.Decoder, but circe library is missing in your project config. If you want to have an instance of io.circe.Decoder[A] provided, please add `"io.circe" %% "circe-core" % CIRCE_VERSION` to your libraryDependencies in build.sbt"""

  val MissingCirceCodec: String =
    """Missing an instance of `CirceCodec` which means you're trying to use io.circe.Codec, but circe library is missing in your project config. If you want to have an instance of io.circe.Codec[A] provided, please add `"io.circe" %% "circe-core" % CIRCE_VERSION` to your libraryDependencies in build.sbt"""

}
