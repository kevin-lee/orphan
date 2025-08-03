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

  trait MyEq[A] {
    def eqv(x: A, y: A): Boolean
  }
  object MyEq {
    def apply[A: MyEq]: MyEq[A] = implicitly[MyEq[A]]
  }

  trait MyHash[A] {
    def hash(x: A): Int
    def eqv(x: A, y: A): Boolean
  }
  object MyHash {
    def apply[A: MyHash]: MyHash[A] = implicitly[MyHash[A]]
  }

  trait MyOrder[A] {
    def compare(x: A, y: A): Int
  }
  object MyOrder {
    def apply[A: MyOrder]: MyOrder[A] = implicitly[MyOrder[A]]
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

    given myNumMyEq: MyEq[MyNum] with {
      @SuppressWarnings(Array("org.wartremover.warts.Equals"))
      override def eqv(x: MyNum, y: MyNum): Boolean = x == y
    }

    given myNumMyHash: MyHash[MyNum] with {

      override def hash(x: MyNum): Int = x.##

      @SuppressWarnings(Array("org.wartremover.warts.Equals"))
      override def eqv(x: MyNum, y: MyNum): Boolean = x == y
    }

    given myNumMyOrder: MyOrder[MyNum] with {
      override def compare(x: MyNum, y: MyNum): Int = x.n.compare(y.n)
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

  private[orphan_instance] trait MyCatsKernelInstances1 extends MyCatsKernelInstances2 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsMonoid\[F\] in method catsMonoid is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsMonoid[F[*]: CatsMonoid]: F[MyNum] = new cats.kernel.Monoid[MyNum] {
      override def empty: MyNum = MyNum(0)

      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances2 extends MyCatsKernelInstances3 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsEq\[F\] in method catsEq is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsEq[F[*]: CatsEq]: F[MyNum] = new cats.kernel.Eq[MyNum] {
      override def eqv(x: MyNum, y: MyNum): Boolean = cats.kernel.Eq[Int].eqv(x.n, y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances3 extends MyCatsKernelInstances4 {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsHash\[F\] in method catsHash is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsHash[F[*]: CatsHash]: F[MyNum] = new cats.kernel.Hash[MyNum] {
      override def hash(x: MyNum): Int = x.##

      override def eqv(x: MyNum, y: MyNum): Boolean = cats.kernel.Eq[Int].eqv(x.n, y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances4 extends OrphanCatsKernel {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsOrder\[F\] in method catsOrder is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsOrder[F[*]: CatsOrder]: F[MyNum] = new cats.kernel.Order[MyNum] {
      override def compare(x: MyNum, y: MyNum): Int = x.n.compare(y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
