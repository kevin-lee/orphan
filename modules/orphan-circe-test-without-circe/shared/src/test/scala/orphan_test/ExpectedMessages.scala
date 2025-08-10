package orphan_test

/** @author Kevin Lee
  * @since 2025-07-28
  */
object ExpectedMessages {

  val ExpectedMessageForCirceEncoder: String =
    """Missing an instance of `CirceEncoder` which means you're trying to use io.circe.Encoder, but circe library is missing in your project config. If you want to have an instance of io.circe.Encoder[A] provided, please add `"io.circe" %% "circe-core" % CIRCE_VERSION` to your libraryDependencies in build.sbt"""

}
