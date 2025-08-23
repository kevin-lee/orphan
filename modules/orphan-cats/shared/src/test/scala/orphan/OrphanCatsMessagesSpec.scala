package orphan

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2025-08-23
  */
object OrphanCatsMessagesSpec extends Properties {
  private val ExpectedDependency = "\"org.typelevel\" %% \"cats-core\" % CATS_VERSION"

  override def tests: List[Test] = List(
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsShow should contain expected strings",
      testExpectedMessageForCatsShow
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsInvariant should contain expected strings",
      testExpectedMessageForCatsInvariant
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsContravariant should contain expected strings",
      testExpectedMessageForCatsContravariant
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsFunctor should contain expected strings",
      testExpectedMessageForCatsFunctor
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsApplicative should contain expected strings",
      testExpectedMessageForCatsApplicative
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsMonad should contain expected strings",
      testExpectedMessageForCatsMonad
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsTraverse should contain expected strings",
      testExpectedMessageForCatsTraverse
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsSemigroup should contain expected strings",
      testExpectedMessageForCatsSemigroup
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsMonoid should contain expected strings",
      testExpectedMessageForCatsMonoid
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsEq should contain expected strings",
      testExpectedMessageForCatsEq
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsHash should contain expected strings",
      testExpectedMessageForCatsHash
    ),
    example(
      "OrphanCatsMessages.ExpectedMessageForCatsOrder should contain expected strings",
      testExpectedMessageForCatsOrder
    ),
  )

  def testExpectedMessageForCatsShow: Result = {
    val message = OrphanCatsMessages.MissingCatsShow
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
    val message = OrphanCatsMessages.MissingCatsInvariant
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
    val message = OrphanCatsMessages.MissingCatsContravariant
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
    val message = OrphanCatsMessages.MissingCatsFunctor
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
    val message = OrphanCatsMessages.MissingCatsApplicative
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
    val message = OrphanCatsMessages.MissingCatsMonad
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
    val message = OrphanCatsMessages.MissingCatsTraverse
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
    val message = OrphanCatsMessages.MissingCatsSemigroup
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
    val message = OrphanCatsMessages.MissingCatsMonoid
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
    val message = OrphanCatsMessages.MissingCatsEq
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
    val message = OrphanCatsMessages.MissingCatsHash
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
    val message = OrphanCatsMessages.MissingCatsOrder
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
