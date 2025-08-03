package orphan_test

/** @author Kevin Lee
  * @since 2025-07-28
  */
object ExpectedMessages {
  val ExpectedMessageForCatsFunctor: String =
    """Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, but cats library is missing in your project config. If you want to have an instance of cats.Functor[F[*]] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsApplicative: String =
    """Missing an instance of `CatsApplicative` which means you're trying to use cats.Applicative, but cats library is missing in your project config. If you want to have an instance of cats.Applicative[F[*]] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsMonad: String =
    """Missing an instance of `CatsMonad` which means you're trying to use cats.Monad, but cats library is missing in your project config. If you want to have an instance of cats.Monad[F[*]] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsTraverse: String =
    """Missing an instance of `CatsTraverse` which means you're trying to use cats.Traverse, but cats library is missing in your project config. If you want to have an instance of cats.Traverse[F[*]] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsSemigroup: String =
    """Missing an instance of `CatsSemigroup` which means you're trying to use cats.kernel.Semigroup, but cats library is missing in your project config. If you want to have an instance of cats.kernel.Semigroup[A] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsMonoid: String =
    """Missing an instance of `CatsMonoid` which means you're trying to use cats.kernel.Monoid, but cats library is missing in your project config. If you want to have an instance of cats.kernel.Monoid[A] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsEq: String =
    """Missing an instance of `CatsEq` which means you're trying to use cats.kernel.Eq, but cats library is missing in your project config. If you want to have an instance of cats.kernel.Eq[A] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

  val ExpectedMessageForCatsHash: String =
    """Missing an instance of `CatsHash` which means you're trying to use cats.kernel.Hash, but cats library is missing in your project config. If you want to have an instance of cats.kernel.Hash[A] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""

}
