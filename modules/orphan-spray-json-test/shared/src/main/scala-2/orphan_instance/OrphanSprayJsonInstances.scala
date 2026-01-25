package orphan_instance

import orphan.OrphanSprayJson

/** @author Kevin Lee
  * @since 2026-01-25
  */
object OrphanSprayJsonInstances {

  final case class MyBox(id: Int, name: String, isActive: Boolean)
//  object MyBox extends MySprayJsonInstances
  object MyBox extends MySprayJsonInstances

  final case class MyBoxForJsonFormat(id: Int, name: String, isActive: Boolean)
  object MyBoxForJsonFormat extends MySprayJsonJsonFormatInstances

  private[orphan_instance] trait MySprayJsonInstances extends MySprayJsonInstances1 {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def sprayJsonJsonWriter[F[*]: SprayJsonJsonWriter]: F[MyBox] = {
      import spray.json.DefaultJsonProtocol.*
      val sprayJsonJsonWriter: spray.json.JsonWriter[MyBox] = spray.json.DefaultJsonProtocol.jsonFormat3(MyBox.apply)
      sprayJsonJsonWriter.asInstanceOf[F[MyBox]] // scalafix:ok asInstanceOf
    }

  }

  private[orphan_instance] trait MySprayJsonInstances1 extends OrphanSprayJson {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def sprayJsonJsonReader[F[*]: SprayJsonJsonReader]: F[MyBox] = {
      import spray.json.DefaultJsonProtocol.*
      val sprayJsonJsonReader: spray.json.JsonReader[MyBox] = spray.json.DefaultJsonProtocol.jsonFormat3(MyBox.apply)
      sprayJsonJsonReader.asInstanceOf[F[MyBox]] // scalafix:ok asInstanceOf
    }

  }

  private[orphan_instance] trait MySprayJsonJsonFormatInstances extends OrphanSprayJson {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def sprayJsonJsonFormat[F[*]: SprayJsonJsonFormat]: F[MyBoxForJsonFormat] = {
      import spray.json.DefaultJsonProtocol.*
      val sprayJsonJsonFormat: spray.json.JsonFormat[MyBoxForJsonFormat] =
        spray.json.DefaultJsonProtocol.jsonFormat3(MyBoxForJsonFormat.apply)
      sprayJsonJsonFormat.asInstanceOf[F[MyBoxForJsonFormat]] // scalafix:ok asInstanceOf
    }

  }

}
