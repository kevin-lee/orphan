package orphan_instance

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

    implicit def myNumMyHash: MyHash[MyNum] = new MyHash[MyNum] {

      override def hash(x: MyNum): Int = x.##

      @SuppressWarnings(Array("org.wartremover.warts.Equals"))
      override def eqv(x: MyNum, y: MyNum): Boolean = x == y
    }

    implicit def myNumMyOrder: MyOrder[MyNum] = new MyOrder[MyNum] {
      override def compare(x: MyNum, y: MyNum): Int = x.n.compare(y.n)
    }

  }

  private[orphan_instance] trait MyCatsKernelInstances extends MyCatsKernelInstances1 {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsSemigroup[F[*]: CatsSemigroup]: F[MyNum] = new cats.kernel.Semigroup[MyNum] {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances1 extends MyCatsKernelInstances2 {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsMonoid[F[*]: CatsMonoid]: F[MyNum] = new cats.kernel.Monoid[MyNum] {
      override def empty: MyNum = MyNum(0)

      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances2 extends MyCatsKernelInstances3 {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsEq[F[*]: CatsEq]: F[MyNum] = new cats.kernel.Eq[MyNum] {
      override def eqv(x: MyNum, y: MyNum): Boolean = cats.kernel.Eq[Int].eqv(x.n, y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances3 extends MyCatsKernelInstances4 {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsHash[F[*]: CatsHash]: F[MyNum] = new cats.kernel.Hash[MyNum] {
      override def hash(x: MyNum): Int = x.##

      override def eqv(x: MyNum, y: MyNum): Boolean = cats.kernel.Eq[Int].eqv(x.n, y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

  private[orphan_instance] trait MyCatsKernelInstances4 extends OrphanCatsKernel {
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    implicit def catsOrder[F[*]: CatsOrder]: F[MyNum] = new cats.kernel.Order[MyNum] {
      override def compare(x: MyNum, y: MyNum): Int = x.n.compare(y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
