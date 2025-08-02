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

  final case class MyNum(n: Int)
  object MyNum extends MyCatsInstances {
    implicit def myNumMySemigroup: MySemigroup[MyNum] = new MySemigroup[MyNum] {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }
  }

  private[orphan_instance] trait MyCatsInstances extends OrphanCatsKernel {
    @nowarn(
      """msg=evidence parameter .+ of type (.+\.)*CatsSemigroup\[F\] in method catsSemigroup is never used"""
    )
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    given catsSemigroup[F[*]: CatsSemigroup]: F[MyNum] = new cats.kernel.Semigroup[MyNum] {
      override def combine(x: MyNum, y: MyNum): MyNum = MyNum(x.n + y.n)
    }.asInstanceOf[F[MyNum]] // scalafix:ok DisableSyntax.asInstanceOf
  }

}
