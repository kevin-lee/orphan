package orphan_instance

import orphan.OrphanCatsKernel

import scala.annotation.nowarn

/** @author Kevin Lee
  * @since 2025-07-28
  */
object OrphanCatsKernelInstances {

  trait MySemigroup[A] {
    def combine(x: A, y: A): A
  }
  object MySemigroup {
    def apply[A: MySemigroup]: MySemigroup[A] = summon[MySemigroup[A]]
  }

  trait MyMonoid[A] {
    def empty: A
    def combine(x: A, y: A): A
  }
  object MyMonoid {
    def apply[A: MyMonoid]: MyMonoid[A] = summon[MyMonoid[A]]
  }

  final case class MyNum(n: Int)
  object MyNum extends MyCatsKernelInstances {
    given myNumMySemigroup: MySemigroup[MyNum] with {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }

    given myNumMonoid: MyMonoid[MyNum] with {
      override def empty: MyNum = MyNum(0)

      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }
  }

  private[orphan_instance] trait MyCatsKernelInstances extends MyCatsKernelInstances1 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsSemigroup\[F\] in method catsSemigroup is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsSemigroup[F[*]: CatsSemigroup]: F[MyNum] = new cats.kernel.Semigroup[MyNum] {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances1 extends OrphanCatsKernel {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsMonoid\[F\] in method catsMonoid is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsMonoid[F[*]: CatsMonoid]: F[MyNum] = new cats.kernel.Monoid[MyNum] {
      override def empty: MyNum = MyNum(0)

      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
