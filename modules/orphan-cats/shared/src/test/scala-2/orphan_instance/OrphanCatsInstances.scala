package orphan_instance

import org.typelevel.scalaccompat.annotation.nowarn213
import orphan.OrphanCats

/** @author Kevin Lee
  * @since 2025-07-28
  */
object OrphanCatsInstances {
  trait MyFunctor[F[*]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
  object MyFunctor {
    def apply[F[*]: MyFunctor]: MyFunctor[F] = implicitly[MyFunctor[F]]
  }

  trait MyApplicative[F[*]] {
    def pure[A](a: A): F[A]
    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
  }
  object MyApplicative {
    def apply[F[*]: MyApplicative]: MyApplicative[F] = implicitly[MyApplicative[F]]
  }

  final case class MyBox[A](a: A)
  object MyBox extends MyCatsInstances2 {

    implicit def myBoxMyFunctor: MyFunctor[MyBox] = new MyFunctor[MyBox] {
      override def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] = fa.copy(f(fa.a))
    }

    implicit def myBoxMyApplicative: MyApplicative[MyBox] = new MyApplicative[MyBox] {
      override def pure[A](a: A): MyBox[A] = MyBox(a)

      override def ap[A, B](ff: MyBox[A => B])(fa: MyBox[A]): MyBox[B] = pure(ff.a(fa.a))
    }

  }

  private[orphan_instance] trait MyCatsInstances2 extends MyCatsInstances1 {
    @nowarn213(
      """msg=evidence parameter .+ of type (.+\.)*CatsFunctor\[F\] in method catsFunctor is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsFunctor[F[*[*]]: CatsFunctor]: F[MyBox] = new cats.Functor[MyBox] {
      override def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] = fa.copy(f(fa.a))
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsInstances1 extends OrphanCats {
    @nowarn213(
      """msg=evidence parameter .+ of type (.+\.)*CatsApplicative\[F\] in method catsApplicative is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsApplicative[F[*[*]]: CatsApplicative]: F[MyBox] = new cats.Applicative[MyBox] {
      override def pure[A](a: A): MyBox[A] = MyBox(a)

      override def ap[A, B](ff: MyBox[A => B])(fa: MyBox[A]): MyBox[B] = pure(ff.a(fa.a))
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
