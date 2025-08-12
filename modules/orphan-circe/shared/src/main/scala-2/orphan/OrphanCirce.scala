package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2025-08-10
  */
trait OrphanCirce {
  final protected type CirceEncoder[F[*]] = OrphanCirce.CirceEncoder[F]
  final protected type CirceDecoder[F[*]] = OrphanCirce.CirceDecoder[F]
}
private[orphan] object OrphanCirce {
  @implicitNotFound(
    msg = "Missing an instance of `CirceEncoder` which means you're trying to use io.circe.Encoder, " +
      "but circe library is missing in your project config. " +
      "If you want to have an instance of io.circe.Encoder[A] provided, " +
      """please add `"io.circe" %% "circe-core" % CIRCE_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CirceEncoder[F[*]]
  private[OrphanCirce] object CirceEncoder {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCirceEncoder: CirceEncoder[io.circe.Encoder] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CirceDecoder` which means you're trying to use io.circe.Decoder, " +
      "but circe library is missing in your project config. " +
      "If you want to have an instance of io.circe.Decoder[A] provided, " +
      """please add `"io.circe" %% "circe-core" % CIRCE_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CirceDecoder[F[*]]
  private[OrphanCirce] object CirceDecoder {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCirceDecoder: CirceDecoder[io.circe.Decoder] =
      null // scalafix:ok DisableSyntax.null
  }

}
