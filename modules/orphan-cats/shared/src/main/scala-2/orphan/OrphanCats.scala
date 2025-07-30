package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2025-07-28
  */
trait OrphanCats {
  final protected type CatsFunctor[F[*[*]]]     = OrphanCats.CatsFunctor[F]
  final protected type CatsApplicative[F[*[*]]] = OrphanCats.CatsApplicative[F]
  final protected type CatsMonad[F[*[*]]]       = OrphanCats.CatsMonad[F]
}
private[orphan] object OrphanCats {
  @implicitNotFound(
    msg = "Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Functor[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsFunctor[F[*[*]]]
  private[OrphanCats] object CatsFunctor {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsFunctor: CatsFunctor[cats.Functor] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsApplicative` which means you're trying to use cats.Applicative, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Applicative[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsApplicative[F[*[*]]]
  private[OrphanCats] object CatsApplicative {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsApplicative: CatsApplicative[cats.Applicative] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsMonad` which means you're trying to use cats.Monad, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Monad[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsMonad[F[*[*]]]
  private[OrphanCats] object CatsMonad {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsMonad: CatsMonad[cats.Monad] =
      null // scalafix:ok DisableSyntax.null
  }

}
