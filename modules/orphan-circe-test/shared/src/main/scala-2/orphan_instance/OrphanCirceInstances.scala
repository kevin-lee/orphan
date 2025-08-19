package orphan_instance

import orphan.OrphanCirce

/** @author Kevin Lee
  * @since 2025-08-10
  */
object OrphanCirceInstances {

  final case class MyBox(id: Int, name: String, isActive: Boolean)
  object MyBox extends MyCirceInstances

  final case class MyBoxForCodec(id: Int, name: String, isActive: Boolean)
  object MyBoxForCodec extends MyCirceCodecInstances

  private[orphan_instance] trait MyCirceInstances extends MyCirceInstances1 {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def circeEncoder[F[*]: CirceEncoder]: F[MyBox] = {
      val myBoxEncoder: io.circe.Encoder[MyBox] = io.circe.generic.semiauto.deriveEncoder[MyBox]
      myBoxEncoder.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
    }
  }

  private[orphan_instance] trait MyCirceInstances1 extends OrphanCirce {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def circeDecoder[F[*]: CirceDecoder]: F[MyBox] = {
      val myBoxDecoder: io.circe.Decoder[MyBox] = io.circe.generic.semiauto.deriveDecoder[MyBox]
      myBoxDecoder.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
    }
  }

  private[orphan_instance] trait MyCirceCodecInstances extends OrphanCirce {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def circeCodec[F[*]: CirceCodec]: F[MyBoxForCodec] = {
      val myBoxCodec: io.circe.Codec[MyBoxForCodec] = io.circe.generic.semiauto.deriveCodec[MyBoxForCodec]
      myBoxCodec.asInstanceOf[F[MyBoxForCodec]] // scalafix:ok DisableSyntax.asInstanceOf
    }
  }

}
