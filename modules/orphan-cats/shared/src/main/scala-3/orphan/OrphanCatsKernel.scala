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
    msg = "Missing an instance of `CatsSemigroup` which means you're trying to use cats.kernel.Semigroup, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.kernel.Semigroup[A] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsSemigroup[F[*]]
  private[OrphanCatsKernel] object CatsSemigroup {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsSemigroup: CatsSemigroup[cats.kernel.Semigroup] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsMonoid` which means you're trying to use cats.kernel.Monoid, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.kernel.Monoid[A] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsMonoid[F[*]]
  private[OrphanCatsKernel] object CatsMonoid {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsMonoid: CatsMonoid[cats.kernel.Monoid] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsEq` which means you're trying to use cats.kernel.Eq, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.kernel.Eq[A] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsEq[F[*]]
  private[OrphanCatsKernel] object CatsEq {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsEq: CatsEq[cats.kernel.Eq] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsHash` which means you're trying to use cats.kernel.Hash, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.kernel.Hash[A] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsHash[F[*]]
  private[OrphanCatsKernel] object CatsHash {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsHash: CatsHash[cats.kernel.Hash] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsOrder` which means you're trying to use cats.kernel.Order, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.kernel.Order[A] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsOrder[F[*]]
  private[OrphanCatsKernel] object CatsOrder {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsOrder: CatsOrder[cats.kernel.Order] =
      null // scalafix:ok DisableSyntax.null
  }

}
