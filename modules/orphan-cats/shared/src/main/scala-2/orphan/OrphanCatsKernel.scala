package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2025-08-02
  */
trait OrphanCatsKernel {
  final protected type CatsSemigroup[F[*]] = OrphanCatsKernel.CatsSemigroup[F]
  final protected type CatsMonoid[F[*]]    = OrphanCatsKernel.CatsMonoid[F]
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
    @inline implicit final def getCatsSemigroup: CatsSemigroup[cats.kernel.Semigroup] =
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
    @inline implicit final def getCatsMonoid: CatsMonoid[cats.kernel.Monoid] =
      null // scalafix:ok DisableSyntax.null
  }

}
