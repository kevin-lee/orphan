package orphan_instance

import org.typelevel.scalaccompat.annotation.nowarn213
import orphan.OrphanCatsKernel

/** @author Kevin Lee
  * @since 2025-07-28
  */
object OrphanCatsKernelInstances {

  trait MySemigroup[A] {
    def combine(x: A, y: A): A
  }
  object MySemigroup {
    def apply[A: MySemigroup]: MySemigroup[A] = implicitly[MySemigroup[A]]
  }

  trait MyMonoid[A] {
    def empty: A
    def combine(x: A, y: A): A
  }
  object MyMonoid {
    def apply[A: MyMonoid]: MyMonoid[A] = implicitly[MyMonoid[A]]
  }

  trait MyEq[A] {
    def eqv(x: A, y: A): Boolean
  }
  object MyEq {
    def apply[A: MyEq]: MyEq[A] = implicitly[MyEq[A]]
  }

  final case class MyNum(n: Int)
  object MyNum extends MyCatsKernelInstances {
    implicit def myNumMySemigroup: MySemigroup[MyNum] = new MySemigroup[MyNum] {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }

    implicit def myNumMonoid: MyMonoid[MyNum] = new MyMonoid[MyNum] {
      override def empty: MyNum = MyNum(0)

      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }

    implicit def myNumMyEq: MyEq[MyNum] = new MyEq[MyNum] {
      @SuppressWarnings(Array("org.wartremover.warts.Equals"))
      override def eqv(x: MyNum, y: MyNum): Boolean = x == y
    }

  }

  private[orphan_instance] trait MyCatsKernelInstances extends MyCatsKernelInstances1 {
    @nowarn213(
      """msg=evidence parameter .+ of type (.+\.)*CatsSemigroup\[F\] in method catsSemigroup is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsSemigroup[F[*]: CatsSemigroup]: F[MyNum] = new cats.kernel.Semigroup[MyNum] {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances1 extends MyCatsKernelInstances2 {
    @nowarn213(
      """msg=evidence parameter .+ of type (.+\.)*CatsMonoid\[F\] in method catsMonoid is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsMonoid[F[*]: CatsMonoid]: F[MyNum] = new cats.kernel.Monoid[MyNum] {
      override def empty: MyNum = MyNum(0)

      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances2 extends OrphanCatsKernel {
    @nowarn213(
      """msg=evidence parameter .+ of type (.+\.)*CatsEq\[F\] in method catsEq is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsEq[F[*]: CatsEq]: F[MyNum] = new cats.kernel.Eq[MyNum] {
      override def eqv(x: MyNum, y: MyNum): Boolean = cats.kernel.Eq[Int].eqv(x.n, y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
