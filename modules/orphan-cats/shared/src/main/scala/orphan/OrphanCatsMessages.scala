package orphan

/** @author Kevin Lee
  * @since 2025-07-28
  */
@SuppressWarnings(Array("org.wartremover.warts.FinalVal", "org.wartremover.warts.PublicInference"))
object OrphanCatsMessages {
  // scalafix:off DisableSyntax.noFinalVal

  final val MissingCatsShow =
    """Missing an instance of `CatsShow` which means you're trying to use cats.Show, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Show[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsInvariant =
    """Missing an instance of `CatsInvariant` which means you're trying to use cats.Invariant, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Invariant[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsContravariant =
    """Missing an instance of `CatsContravariant` which means you're trying to use cats.Contravariant, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Contravariant[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsFunctor =
    """Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Functor[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsApplicative =
    """Missing an instance of `CatsApplicative` which means you're trying to use cats.Applicative, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Applicative[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsMonad =
    """Missing an instance of `CatsMonad` which means you're trying to use cats.Monad, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Monad[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsTraverse =
    """Missing an instance of `CatsTraverse` which means you're trying to use cats.Traverse, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Traverse[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsSemigroup =
    """Missing an instance of `CatsSemigroup` which means you're trying to use cats.kernel.Semigroup, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Semigroup[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsMonoid =
    """Missing an instance of `CatsMonoid` which means you're trying to use cats.kernel.Monoid, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Monoid[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsEq =
    """Missing an instance of `CatsEq` which means you're trying to use cats.kernel.Eq, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Eq[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsHash =
    """Missing an instance of `CatsHash` which means you're trying to use cats.kernel.Hash, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Hash[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val MissingCatsOrder =
    """Missing an instance of `CatsOrder` which means you're trying to use cats.kernel.Order, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Order[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  // scalafix: on
}
