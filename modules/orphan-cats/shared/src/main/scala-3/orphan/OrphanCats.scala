package orphan

import scala.annotation.implicitNotFound

/** @author Kevin Lee
  * @since 2025-07-28
  */
trait OrphanCats {
  final protected type CatsShow[F[*]] = OrphanCats.CatsShow[F]

  final protected type CatsInvariant[F[*[*]]]     = OrphanCats.CatsInvariant[F]
  final protected type CatsContravariant[F[*[*]]] = OrphanCats.CatsContravariant[F]

  final protected type CatsFunctor[F[*[*]]]     = OrphanCats.CatsFunctor[F]
  final protected type CatsApplicative[F[*[*]]] = OrphanCats.CatsApplicative[F]
  final protected type CatsMonad[F[*[*]]]       = OrphanCats.CatsMonad[F]
  final protected type CatsTraverse[F[*[*]]]    = OrphanCats.CatsTraverse[F]
}
private[orphan] object OrphanCats {

  @implicitNotFound(
    msg = "Missing an instance of `CatsShow` which means you're trying to use cats.Show, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Show[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsShow[F[*]]
  private[OrphanCats] object CatsShow {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsShow: CatsShow[cats.Show] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsContravariant` which means you're trying to use cats.Contravariant, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Contravariant[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsContravariant[F[*[*]]]
  private[OrphanCats] object CatsContravariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsContravariant: CatsContravariant[cats.Contravariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsInvariant` which means you're trying to use cats.Invariant, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Invariant[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsInvariant[F[*[*]]]
  private[OrphanCats] object CatsInvariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsInvariant: CatsInvariant[cats.Invariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Functor[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsFunctor[F[*[*]]]
  private[OrphanCats] object CatsFunctor {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsFunctor: CatsFunctor[cats.Functor] =
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
    final inline given getCatsApplicative: CatsApplicative[cats.Applicative] =
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
    final inline given getCatsMonad: CatsMonad[cats.Monad] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = "Missing an instance of `CatsTraverse` which means you're trying to use cats.Traverse, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Traverse[F[*]] provided, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed protected trait CatsTraverse[F[*[*]]]
  private[OrphanCats] object CatsTraverse {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsTraverse: CatsTraverse[cats.Traverse] =
      null // scalafix:ok DisableSyntax.null
  }

}
