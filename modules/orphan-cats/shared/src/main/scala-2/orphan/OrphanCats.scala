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
    msg = OrphanCatsMessages.MisingCatsShow
  )
  sealed protected trait CatsShow[F[*]]
  private[OrphanCats] object CatsShow {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsShow: CatsShow[cats.Show] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsInvariant
  )
  sealed protected trait CatsInvariant[F[*[*]]]
  private[OrphanCats] object CatsInvariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsInvariant: CatsInvariant[cats.Invariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsContravariant
  )
  sealed protected trait CatsContravariant[F[*[*]]]
  private[OrphanCats] object CatsContravariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsContravariant: CatsContravariant[cats.Contravariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsFunctor
  )
  sealed protected trait CatsFunctor[F[*[*]]]
  private[OrphanCats] object CatsFunctor {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsFunctor: CatsFunctor[cats.Functor] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsApplicative
  )
  sealed protected trait CatsApplicative[F[*[*]]]
  private[OrphanCats] object CatsApplicative {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsApplicative: CatsApplicative[cats.Applicative] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsMonad
  )
  sealed protected trait CatsMonad[F[*[*]]]
  private[OrphanCats] object CatsMonad {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsMonad: CatsMonad[cats.Monad] =
      null // scalafix:ok DisableSyntax.null
  }

  @implicitNotFound(
    msg = OrphanCatsMessages.MisingCatsTraverse
  )
  sealed protected trait CatsTraverse[F[*[*]]]
  private[OrphanCats] object CatsTraverse {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsTraverse: CatsTraverse[cats.Traverse] =
      null // scalafix:ok DisableSyntax.null
  }

}
