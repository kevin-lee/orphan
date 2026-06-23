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
    msg = OrphanCatsMessages.MissingCatsShow
  )
  sealed protected trait CatsShow[F[*]]
  object CatsShow {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsShow: CatsShow[cats.Show] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MissingCatsInvariant
  )
  sealed protected trait CatsInvariant[F[*[*]]]
  object CatsInvariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsInvariant: CatsInvariant[cats.Invariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MissingCatsContravariant
  )
  sealed protected trait CatsContravariant[F[*[*]]]
  object CatsContravariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsContravariant: CatsContravariant[cats.Contravariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MissingCatsFunctor
  )
  sealed protected trait CatsFunctor[F[*[*]]]
  object CatsFunctor {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsFunctor: CatsFunctor[cats.Functor] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MissingCatsApplicative
  )
  sealed protected trait CatsApplicative[F[*[*]]]
  object CatsApplicative {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsApplicative: CatsApplicative[cats.Applicative] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MissingCatsMonad
  )
  sealed protected trait CatsMonad[F[*[*]]]
  object CatsMonad {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsMonad: CatsMonad[cats.Monad] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MissingCatsTraverse
  )
  sealed protected trait CatsTraverse[F[*[*]]]
  object CatsTraverse {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    final inline given getCatsTraverse: CatsTraverse[cats.Traverse] =
      null // scalafix:ok DisableSyntax.null
  }

}
