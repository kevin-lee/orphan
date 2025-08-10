package orphan_instance

import org.typelevel.scalaccompat.annotation.nowarn213
import orphan.OrphanCirce

/** @author Kevin Lee
  * @since 2025-08-10
  */
object OrphanCirceInstances {

  final case class MyBox(id: Int, name: String, isActive: Boolean)
  object MyBox extends MyCirceInstances

  private[orphan_instance] trait MyCirceInstances extends OrphanCirce {
    @nowarn213(
      """msg=evidence parameter .+ of type (.+\.)*CirceEncoder\[F\] in method circeEncoder is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def circeEncoder[F[*]: CirceEncoder]: F[MyBox] = {
      val myBoxEncoder: io.circe.Encoder[MyBox] = io.circe.generic.semiauto.deriveEncoder[MyBox]
      myBoxEncoder.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
    }
  }

}
