package orphan

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2025-08-23
  */
object ExpectedMessagesSpec extends Properties {
  private val ExpectedDependency = "\"org.typelevel\" %% \"cats-core\" % CATS_VERSION"

  override def tests: List[Test] = List(
    example(
      "ExpectedMessages.ExpectedMessageForCatsShow should contain expected strings",
      testExpectedMessageForCatsShow
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsInvariant should contain expected strings",
      testExpectedMessageForCatsInvariant
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsContravariant should contain expected strings",
      testExpectedMessageForCatsContravariant
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsFunctor should contain expected strings",
      testExpectedMessageForCatsFunctor
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsApplicative should contain expected strings",
      testExpectedMessageForCatsApplicative
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsMonad should contain expected strings",
      testExpectedMessageForCatsMonad
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsTraverse should contain expected strings",
      testExpectedMessageForCatsTraverse
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsSemigroup should contain expected strings",
      testExpectedMessageForCatsSemigroup
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsMonoid should contain expected strings",
      testExpectedMessageForCatsMonoid
    ),
    example("ExpectedMessages.ExpectedMessageForCatsEq should contain expected strings", testExpectedMessageForCatsEq),
    example(
      "ExpectedMessages.ExpectedMessageForCatsHash should contain expected strings",
      testExpectedMessageForCatsHash
    ),
    example(
      "ExpectedMessages.ExpectedMessageForCatsOrder should contain expected strings",
      testExpectedMessageForCatsOrder
    ),
  )

  def testExpectedMessageForCatsShow: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsShow
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsShow")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Show[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsInvariant: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsInvariant
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsInvariant")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Invariant[F[*]] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsContravariant: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsContravariant
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsContravariant")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Contravariant[F[*]] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsFunctor: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsFunctor
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsFunctor")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Functor[F[*]] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsApplicative: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsApplicative
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsApplicative")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Applicative[F[*]] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsMonad: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsMonad
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsMonad")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Monad[F[*]] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsTraverse: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsTraverse
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsTraverse")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.Traverse[F[*]] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsSemigroup: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsSemigroup
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsSemigroup")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.kernel.Semigroup[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsMonoid: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsMonoid
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsMonoid")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.kernel.Monoid[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsEq: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsEq
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsEq")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.kernel.Eq[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsHash: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsHash
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsHash")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.kernel.Hash[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }

  def testExpectedMessageForCatsOrder: Result = {
    val message = ExpectedMessages.ExpectedMessageForCatsOrder
    Result.all(
      List(
        Result.diffNamed("Should contain instance name", message, "CatsOrder")(_.contains(_)),
        Result.diffNamed(
          "Should contain cats type",
          message,
          "If you want to have an instance of cats.kernel.Order[A] provided, "
        )(_.contains(_)),
        Result.diffNamed("Should contain dependency", message, ExpectedDependency)(_.contains(_))
      )
    )
  }
}
