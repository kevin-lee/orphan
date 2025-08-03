package orphan_instance

import orphan.OrphanCats

import scala.annotation.nowarn
import scala.annotation.tailrec

/** @author Kevin Lee
  * @since 2025-07-28
  */
object OrphanCatsInstances {
  trait MyFunctor[F[*]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
  object MyFunctor {
    def apply[F[*]: MyFunctor]: MyFunctor[F] = summon[MyFunctor[F]]
  }

  trait MyApplicative[F[*]] {
    def pure[A](a: A): F[A]
    def map[A, B](fa: F[A])(f: A => B): F[B] = ap(pure(f))(fa)
    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
  }
  object MyApplicative {
    def apply[F[*]: MyApplicative]: MyApplicative[F] = summon[MyApplicative[F]]
  }

  trait MyMonad[F[*]] {
    def pure[A](a: A): F[A]
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
    def tailRecM[A, B](a: A)(f: A => F[Either[A, B]]): F[B]
  }
  object MyMonad {
    def apply[F[*]: MyMonad]: MyMonad[F] = summon[MyMonad[F]]
  }

  class MyEval[A](val thunk: () => A) extends AnyVal
  object MyEval {
    def apply[A](a: => A): MyEval[A] = new MyEval[A](() => a)
  }

  trait MyTraverse[F[*]] {
    def traverse[G[_]: MyApplicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
    def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B
    def foldRight[A, B](fa: F[A], lb: MyEval[B])(f: (A, MyEval[B]) => MyEval[B]): MyEval[B]
  }
  object MyTraverse {
    def apply[F[*]: MyTraverse]: MyTraverse[F] = summon[MyTraverse[F]]
  }

  final case class MyBox[A](a: A)
  object MyBox extends CatsInstances {

    given myBoxMyFunctor: MyFunctor[MyBox] with {
      override def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] = fa.copy(f(fa.a))
    }

    given myBoxMyApplicative: MyApplicative[MyBox] with {
      override def pure[A](a: A): MyBox[A] = MyBox(a)

      override def ap[A, B](ff: MyBox[A => B])(fa: MyBox[A]): MyBox[B] = pure(ff.a(fa.a))
    }

    given myBoxMyMonad: MyMonad[MyBox] with {
      override def pure[A](a: A): MyBox[A] = MyBox(a)

      override def flatMap[A, B](fa: MyBox[A])(f: A => MyBox[B]): MyBox[B] = f(fa.a)

      @tailrec
      override def tailRecM[A, B](a: A)(f: A => MyBox[Either[A, B]]): MyBox[B] = f(a) match {
        case MyBox(Right(b)) => pure(b)
        case MyBox(Left(a)) => tailRecM(a)(f)
      }
    }

    given myBoxMyTraverse: MyTraverse[MyBox] with {
      override def traverse[G[_]: MyApplicative, A, B](fa: MyBox[A])(f: A => G[B]): G[MyBox[B]] =
        MyApplicative[G].map(f(fa.a))(MyBox(_))

      override def foldLeft[A, B](fa: MyBox[A], b: B)(f: (B, A) => B): B = f(b, fa.a)

      override def foldRight[A, B](fa: MyBox[A], lb: MyEval[B])(f: (A, MyEval[B]) => MyEval[B]): MyEval[B] =
        f(fa.a, lb)
    }

  }

  private[orphan_instance] trait CatsInstances extends CatsInstances1 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsFunctor\[F\] in method catsFunctor is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsFunctor[F[*[*]]: CatsFunctor]: F[MyBox] = new cats.Functor[MyBox] {
      override def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] = fa.copy(f(fa.a))
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait CatsInstances1 extends MyCatsInstances2 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsFunctor\[F\] in method catsFunctor is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsApplicative[F[*[*]]: CatsApplicative]: F[MyBox] = new cats.Applicative[MyBox] {
      override def pure[A](a: A): MyBox[A] = MyBox(a)

      override def ap[A, B](ff: MyBox[A => B])(fa: MyBox[A]): MyBox[B] = pure(ff.a(fa.a))
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsInstances2 extends MyCatsInstances3 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsMonad\[F\] in method catsMonad is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsMonad[F[*[*]]: CatsMonad]: F[MyBox] = new cats.Monad[MyBox] {
      override def pure[A](x: A): MyBox[A] = MyBox(x)

      override def flatMap[A, B](fa: MyBox[A])(f: A => MyBox[B]): MyBox[B] = f(fa.a)

      @tailrec
      override def tailRecM[A, B](a: A)(f: A => MyBox[Either[A, B]]): MyBox[B] = f(a) match {
        case MyBox(Right(b)) => pure(b)
        case MyBox(Left(a)) => tailRecM(a)(f)
      }
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsInstances3 extends OrphanCats {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsTraverse\[F\] in method catsTraverse is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsTraverse[F[*[*]]: CatsTraverse]: F[MyBox] = new cats.Traverse[MyBox] {
      override def traverse[G[_]: cats.Applicative, A, B](fa: MyBox[A])(f: A => G[B]): G[MyBox[B]] =
        cats.Applicative[G].map(f(fa.a))(MyBox(_))

      override def foldLeft[A, B](fa: MyBox[A], b: B)(f: (B, A) => B): B = f(b, fa.a)

      override def foldRight[A, B](fa: MyBox[A], lb: cats.Eval[B])(f: (A, cats.Eval[B]) => cats.Eval[B]): cats.Eval[B] =
        f(fa.a, lb)
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
