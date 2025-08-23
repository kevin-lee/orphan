package orphan

/** @author Kevin Lee
  * @since 2025-07-28
  */
@SuppressWarnings(Array("org.wartremover.warts.FinalVal", "org.wartremover.warts.PublicInference"))
object ExpectedMessages {
  // scalafix:off DisableSyntax.noFinalVal

  final val ExpectedMessageForCatsShow =
    """Missing an instance of `CatsShow` which means you're trying to use cats.Show, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Show[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsInvariant =
    """Missing an instance of `CatsInvariant` which means you're trying to use cats.Invariant, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Invariant[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsContravariant =
    """Missing an instance of `CatsContravariant` which means you're trying to use cats.Contravariant, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Contravariant[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsFunctor =
    """Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Functor[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsApplicative =
    """Missing an instance of `CatsApplicative` which means you're trying to use cats.Applicative, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Applicative[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsMonad =
    """Missing an instance of `CatsMonad` which means you're trying to use cats.Monad, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Monad[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsTraverse =
    """Missing an instance of `CatsTraverse` which means you're trying to use cats.Traverse, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.Traverse[F[*]] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsSemigroup =
    """Missing an instance of `CatsSemigroup` which means you're trying to use cats.kernel.Semigroup, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Semigroup[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsMonoid =
    """Missing an instance of `CatsMonoid` which means you're trying to use cats.kernel.Monoid, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Monoid[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsEq =
    """Missing an instance of `CatsEq` which means you're trying to use cats.kernel.Eq, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Eq[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsHash =
    """Missing an instance of `CatsHash` which means you're trying to use cats.kernel.Hash, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Hash[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  final val ExpectedMessageForCatsOrder =
    """Missing an instance of `CatsOrder` which means you're trying to use cats.kernel.Order, """ +
      """but cats library is missing in your project config. """ +
      """If you want to have an instance of cats.kernel.Order[A] provided, """ +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  // scalafix: on
}
