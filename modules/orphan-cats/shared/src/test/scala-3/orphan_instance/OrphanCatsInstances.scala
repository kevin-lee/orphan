package orphan_instance

import orphan.OrphanCats

import scala.annotation.nowarn

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

  final case class MyBox[A](a: A)
  object MyBox extends OrphanCats {

    given myboxMyFunctor: MyFunctor[MyBox] with {
      override def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] = fa.copy(f(fa.a))
    }

    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsFunctor\[F\] in method catsFunctor is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsFunctor[F[*[*]]: CatsFunctor]: F[MyBox] = new cats.Functor[MyBox] {
      override def map[A, B](fa: MyBox[A])(f: A => B): MyBox[B] = fa.copy(f(fa.a))
    }.asInstanceOf[F[MyBox]] // scalafix:ok DisableSyntax.asInstanceOf
  }
}
