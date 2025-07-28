package orphan_test

/** @author Kevin Lee
  * @since 2025-07-28
  */
object ExpectedMessages {
  val ExpectedMessageForCatsFunctor: String =
    """error: Missing an instance of `CatsFunctor` which means you're trying to use cats.Functor, but cats library is missing in your project config. If you want to have an instance of cats.Functor[F[*]] provided, please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt
      |orphan_instance.OrphanCatsInstances.MyBox.catsFunctor
      |                                          ^""".stripMargin
}
