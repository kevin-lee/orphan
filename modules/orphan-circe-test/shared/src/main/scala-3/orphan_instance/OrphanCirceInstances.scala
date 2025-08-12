package orphan_instance

import scala.annotation.nowarn
import orphan.OrphanCirce

/** @author Kevin Lee
  * @since 2025-08-10
  */
object OrphanCirceInstances {

  final case class MyBox(id: Int, name: String, isActive: Boolean)
  object MyBox extends MyCirceInstances

  private[orphan_instance] trait MyCirceInstances extends MyCirceInstances1 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CirceEncoder\[F\] in method circeEncoder is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given circeEncoder[F[*]: CirceEncoder]: F[MyBox] = {
      io.circe.generic.semiauto.deriveEncoder[MyBox].asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
    }
  }

  private[orphan_instance] trait MyCirceInstances1 extends OrphanCirce {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CirceDecoder\[F\] in method circeDecoder is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given circeDecoder[F[*]: CirceDecoder]: F[MyBox] = {
      io.circe.generic.semiauto.deriveDecoder[MyBox].asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
    }
  }

}
