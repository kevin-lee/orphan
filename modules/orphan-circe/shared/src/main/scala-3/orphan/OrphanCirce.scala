package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2025-08-10
  */
trait OrphanCirce {
  final protected type CirceEncoder[F[*]] = OrphanCirce.CirceEncoder[F]
  final protected type CirceDecoder[F[*]] = OrphanCirce.CirceDecoder[F]
  final protected type CirceCodec[F[*]]   = OrphanCirce.CirceCodec[F]
}
private[orphan] object OrphanCirce {
  @implicitNotFound(
    msg = OrphanCirceMessages.MissingCirceEncoder
  )
  sealed protected trait CirceEncoder[F[*]]
  private[OrphanCirce] object CirceEncoder {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCirceEncoder: CirceEncoder[io.circe.Encoder] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCirceMessages.MissingCirceDecoder
  )
  sealed protected trait CirceDecoder[F[*]]
  private[OrphanCirce] object CirceDecoder {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCirceDecoder: CirceDecoder[io.circe.Decoder] =
      null // scalafix:ok DisableSyntax.null
  }

  final abstract private class CirceIsAvailable
  private object CirceIsAvailable {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCirceIsAvailable[F[*]: CirceEncoder]: CirceIsAvailable =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCirceMessages.MissingCirceCodec
  )
  sealed protected trait CirceCodec[F[*]]
  private[OrphanCirce] object CirceCodec {
    type CirceCodecEncoderDecoder[A] = io.circe.Codec[A] & io.circe.Encoder[A] & io.circe.Decoder[A]

    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    given getCirceCodec(using circeIsAvailable: CirceIsAvailable): CirceCodec[CirceCodecEncoderDecoder] =
      null // scalafix:ok DisableSyntax.null
  }

}
