package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2026-01-22
  */
trait OrphanSprayJson {
  final protected type SprayJsonJsonReader[F[*]] = OrphanSprayJson.SprayJsonJsonReader[F]
  final protected type SprayJsonJsonWriter[F[*]] = OrphanSprayJson.SprayJsonJsonWriter[F]
  final protected type SprayJsonJsonFormat[F[*]] = OrphanSprayJson.SprayJsonJsonFormat[F]
}
private[orphan] object OrphanSprayJson {

  @implicitNotFound(
    msg = OrphanSprayJsonMessages.MissingSprayJsonJsonWriter
  )
  sealed protected trait SprayJsonJsonWriter[F[*]]
  private[orphan] object SprayJsonJsonWriter {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getSprayJsonJsonWriter: SprayJsonJsonWriter[spray.json.JsonWriter] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanSprayJsonMessages.MissingSprayJsonJsonReader
  )
  sealed protected trait SprayJsonJsonReader[F[*]]
  private[orphan] object SprayJsonJsonReader {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getSprayJsonJsonReader: SprayJsonJsonReader[spray.json.JsonReader] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanSprayJsonMessages.MissingSprayJsonJsonFormat
  )
  sealed protected trait SprayJsonJsonFormat[F[*]]
  private[orphan] object SprayJsonJsonFormat {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getSprayJsonJsonFormat: SprayJsonJsonFormat[spray.json.JsonFormat] =
      null // scalafix:ok DisableSyntax.null
  }

}
