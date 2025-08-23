package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2025-08-02
  */
trait OrphanCatsKernel {
  final protected type CatsSemigroup[F[*]] = OrphanCatsKernel.CatsSemigroup[F]
  final protected type CatsMonoid[F[*]]    = OrphanCatsKernel.CatsMonoid[F]
  final protected type CatsEq[F[*]]        = OrphanCatsKernel.CatsEq[F]
  final protected type CatsHash[F[*]]      = OrphanCatsKernel.CatsHash[F]
  final protected type CatsOrder[F[*]]     = OrphanCatsKernel.CatsOrder[F]
}
private[orphan] object OrphanCatsKernel {

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsSemigroup
  )
  sealed protected trait CatsSemigroup[F[*]]
  private[OrphanCatsKernel] object CatsSemigroup {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsSemigroup: CatsSemigroup[cats.kernel.Semigroup] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsMonoid
  )
  sealed protected trait CatsMonoid[F[*]]
  private[OrphanCatsKernel] object CatsMonoid {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsMonoid: CatsMonoid[cats.kernel.Monoid] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsEq
  )
  sealed protected trait CatsEq[F[*]]
  private[OrphanCatsKernel] object CatsEq {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsEq: CatsEq[cats.kernel.Eq] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsHash
  )
  sealed protected trait CatsHash[F[*]]
  private[OrphanCatsKernel] object CatsHash {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsHash: CatsHash[cats.kernel.Hash] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsOrder
  )
  sealed protected trait CatsOrder[F[*]]
  private[OrphanCatsKernel] object CatsOrder {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsOrder: CatsOrder[cats.kernel.Order] =
      null // scalafix:ok DisableSyntax.null
  }

}
