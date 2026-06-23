import sbtcrossproject.CrossProject

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / crossScalaVersions := props.CrossScalaVersions
ThisBuild / organization := props.Org
ThisBuild / organizationName := "Kevin's Code"

ThisBuild / developers := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}"),
  )
)

ThisBuild / homepage := Some(
  url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}")
)
ThisBuild / scmInfo :=
  Some(
    ScmInfo(
      url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"),
      s"git@github.com:${props.GitHubUsername}/${props.RepoName}.git",
    )
  )
ThisBuild / licenses := props.licenses

ThisBuild / scalafixDependencies += "com.github.xuwei-k" %% "scalafix-rules" % props.XuweiKScalafixRulesVersion

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3"))
    ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
  else
    ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
)

lazy val orphan = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(noPublish)
  .aggregate(
    orphanCatsJvm,
    orphanCatsJs,
    orphanCatsNative,
    orphanCatsTestWithoutCatsJvm,
    orphanCatsTestWithoutCatsJs,
    orphanCatsTestWithoutCatsNative,
    orphanCatsTestWithCatsJvm,
    orphanCatsTestWithCatsJs,
    orphanCatsTestWithCatsNative,
    ///
    orphanCirceJvm,
    orphanCirceJs,
    orphanCirceNative,
    orphanCirceTestJvm,
    orphanCirceTestJs,
    orphanCirceTestNative,
    orphanCirceTestWithoutCirceJvm,
    orphanCirceTestWithoutCirceJs,
    orphanCirceTestWithoutCirceNative,
    orphanCirceTestWithCirceJvm,
    orphanCirceTestWithCirceJs,
    orphanCirceTestWithCirceNative,
    ///
    orphanSprayJsonJvm,
    orphanSprayJsonTestJvm,
    orphanSprayJsonTestWithoutSprayJsonJvm,
    orphanSprayJsonTestWithSprayJsonJvm,
  )

lazy val orphanCats       = module("cats", crossProject(JVMPlatform, JSPlatform, NativePlatform))
  .settings(
    libraryDependencies ++= List(libs.cats.value % Optional) ++ (
      if (isScala3(scalaVersion.value)) List.empty
      else
        List(
//          libs.scalacCompatAnnotation,
          libs.tests.scalaReflect.value
        )
    ),
  )
lazy val orphanCatsJvm    = orphanCats.jvm
lazy val orphanCatsJs     = orphanCats.js.settings(jsCommonSettings)
lazy val orphanCatsNative = orphanCats.native.settings(nativeSettings)

lazy val orphanCatsTestWithoutCats       =
  module("cats-test-without-cats", crossProject(JVMPlatform, JSPlatform, NativePlatform))
    .settings(noPublish)
    .settings(
      libraryDependencies ++= List(libs.tests.extrasTestingTools.value),
      libraryDependencies ~= (libs => libs.filterNot(_.name.startsWith("cats"))),
    )
    .dependsOn(orphanCats % props.IncludeTest)
lazy val orphanCatsTestWithoutCatsJvm    = orphanCatsTestWithoutCats.jvm
lazy val orphanCatsTestWithoutCatsJs     = orphanCatsTestWithoutCats.js.settings(jsCommonSettings)
lazy val orphanCatsTestWithoutCatsNative = orphanCatsTestWithoutCats.native.settings(nativeSettings)

lazy val orphanCatsTestWithCats   = module("cats-test-with-cats", crossProject(JVMPlatform, JSPlatform, NativePlatform))
  .settings(noPublish)
  .settings(
    libraryDependencies ++= List(libs.cats.value % Test),
  )
  .dependsOn(orphanCats % props.IncludeTest)
lazy val orphanCatsTestWithCatsJvm = orphanCatsTestWithCats.jvm
lazy val orphanCatsTestWithCatsJs = orphanCatsTestWithCats.js.settings(jsCommonSettings)
lazy val orphanCatsTestWithCatsNative = orphanCatsTestWithCats.native.settings(nativeSettings)

lazy val orphanCirce       = module("circe", crossProject(JVMPlatform, JSPlatform, NativePlatform))
  .settings(
    libraryDependencies ++= List(
      libs.circeCore.value % Optional,
    ) ++ (
      if (isScala3(scalaVersion.value)) List.empty
      else
        List(
//          libs.scalacCompatAnnotation,
          libs.tests.scalaReflect.value
        )
    ),
  )
lazy val orphanCirceJvm    = orphanCirce.jvm
lazy val orphanCirceJs     = orphanCirce.js.settings(jsCommonSettings)
lazy val orphanCirceNative = orphanCirce.native.settings(nativeSettings)

lazy val orphanCirceTest       = module("circe-test", crossProject(JVMPlatform, JSPlatform, NativePlatform))
  .settings(noPublish)
  .settings(
    libraryDependencies ++= List(libs.circeGeneric.value % Optional),
  )
  .dependsOn(orphanCirce % props.IncludeTest)
lazy val orphanCirceTestJvm    = orphanCirceTest.jvm
lazy val orphanCirceTestJs     = orphanCirceTest.js.settings(jsCommonSettings)
lazy val orphanCirceTestNative = orphanCirceTest.native.settings(nativeSettings)

lazy val orphanCirceTestWithoutCirce       =
  module("circe-test-without-circe", crossProject(JVMPlatform, JSPlatform, NativePlatform))
    .settings(noPublish)
    .settings(
      libraryDependencies ++= List(libs.tests.extrasTestingTools.value),
      Test / libraryDependencies ~= (libs => libs.filterNot(_.name.startsWith("circe")))
    )
    .dependsOn(orphanCirceTest % props.IncludeTest)
lazy val orphanCirceTestWithoutCirceJvm    = orphanCirceTestWithoutCirce.jvm
lazy val orphanCirceTestWithoutCirceJs     = orphanCirceTestWithoutCirce.js.settings(jsCommonSettings)
lazy val orphanCirceTestWithoutCirceNative = orphanCirceTestWithoutCirce.native.settings(nativeSettings)

lazy val orphanCirceTestWithCirce       =
  module("circe-test-with-circe", crossProject(JVMPlatform, JSPlatform, NativePlatform))
    .settings(noPublish)
    .settings(
      libraryDependencies ++= List(
        libs.circeCore.value    % Test,
        libs.circeGeneric.value % Test,
        libs.circeJawn.value    % Test,
        libs.circeLiteral.value % Test,
        libs.circeParser.value  % Test,
      ),
    )
    .dependsOn(orphanCirceTest % props.IncludeTest)
lazy val orphanCirceTestWithCirceJvm    = orphanCirceTestWithCirce.jvm
lazy val orphanCirceTestWithCirceJs     = orphanCirceTestWithCirce.js.settings(jsCommonSettings)
lazy val orphanCirceTestWithCirceNative = orphanCirceTestWithCirce.native.settings(nativeSettings)

lazy val orphanSprayJson    = module("spray-json", crossProject(JVMPlatform))
  .settings(
    libraryDependencies ++= List(
      libs.sprayJson % Optional,
    ),
  )
lazy val orphanSprayJsonJvm = orphanSprayJson.jvm

lazy val orphanSprayJsonTest    = module("spray-json-test", crossProject(JVMPlatform))
  .settings(noPublish)
  .settings(
    libraryDependencies ++= List(libs.sprayJson % Optional),
  )
  .dependsOn(orphanSprayJson % props.IncludeTest)
lazy val orphanSprayJsonTestJvm = orphanSprayJsonTest.jvm

lazy val orphanSprayJsonTestWithoutSprayJson    =
  module("spray-json-test-without-spray-json", crossProject(JVMPlatform))
    .settings(noPublish)
    .settings(
      libraryDependencies ++= List(libs.tests.extrasTestingTools.value),
      Test / libraryDependencies ~= (libs => libs.filterNot(_.name.startsWith("spray-json")))
    )
    .dependsOn(orphanSprayJsonTest % props.IncludeTest)
lazy val orphanSprayJsonTestWithoutSprayJsonJvm = orphanSprayJsonTestWithoutSprayJson.jvm

lazy val orphanSprayJsonTestWithSprayJson    =
  module("spray-json-test-with-spray-json", crossProject(JVMPlatform))
    .settings(noPublish)
    .settings(
      libraryDependencies ++= List(
        libs.sprayJson % Test,
      ),
    )
    .dependsOn(orphanSprayJsonTest % props.IncludeTest)
lazy val orphanSprayJsonTestWithSprayJsonJvm = orphanSprayJsonTestWithSprayJson.jvm

/* ===== orphan #96 "non-accessible instance" guard modules (Scala 3.8.4 / 3.10-nightly) =====
 * Scala 3 PRs #25516/#25608/#25367 stop inferring implicit/given instances from
 * non-accessible companion objects. These modules recompile the existing
 * marker/instance/spec sources under the newer Scala 3 versions (where that change
 * lands) to prove the markers still resolve. They are NOT aggregated and NOT published.
 * They disable WartRemover/Tpolecat/semanticdb/scalafix because those per-Scala-version
 * compiler plugins have no 3.8.4/3.10-nightly artifacts.
 */

/** Resolve the latest Scala 3.10 nightly. Precedence:
  *   1. `ORPHAN_SCALA_310_NIGHTLY` env var (CI pins the exact nightly);
  *   2. the nightlies `maven-metadata.xml` (latest `3.10.x-...-NIGHTLY`);
  *   3. a pinned known-good fallback (keeps offline/CI deterministic).
  */
def latest310Nightly(): String = {
  val fallback = "3.10.0-RC1-bin-20260621-b3a04ea-NIGHTLY"
  sys.env.get("ORPHAN_SCALA_310_NIGHTLY").filter(_.nonEmpty).getOrElse {
    val metadataUrl =
      "https://repo.scala-lang.org/artifactory/maven-nightlies/org/scala-lang/scala3-library_3/maven-metadata.xml"
    try {
      val src = scala.io.Source.fromURL(metadataUrl)("UTF-8")

      val body =
        try src.mkString
        finally src.close()

      val versionRe = "<version>(3\\.10\\.[^<]*-NIGHTLY)</version>".r
      versionRe.findAllMatchIn(body).map(_.group(1)).toList.lastOption.getOrElse(fallback)
    } catch {
      case _: Throwable => fallback
    }
  }
}

lazy val nonAccessibleInstanceProps = new {
  val Scala3Baseline                  = props.Scala3Version // "3.3.3"
  val Scala384                        = "3.8.4"
  lazy val Scala310Nightly: String    = latest310Nightly()
  lazy val ScalaVersions: Seq[String] =
    Seq(Scala3Baseline, Scala384, Scala310Nightly)
  val ScalaNightlyResolver            =
    "scala-nightlies".at("https://repo.scala-lang.org/artifactory/maven-nightlies/")
}

/** Bare JVM-only test project: does NOT use the module() helper, so none of
  * WartRemover/Tpolecat/semanticdb/scalafix is injected.
  */
def nonAccessibleInstanceModule(id: String): Project =
  Project(id = id, base = file(s"modules/$id"))
    .disablePlugins(wartremover.WartRemover, org.typelevel.sbt.tpolecat.TpolecatPlugin)
    .settings(noPublish)
    .settings(
      name := id,
      fork := true,
      scalaVersion := nonAccessibleInstanceProps.Scala3Baseline,
      crossScalaVersions := nonAccessibleInstanceProps.ScalaVersions,
      semanticdbEnabled := false,
      scalafixConfig := None,
      /* kind-projector is required for the markers' `F[*[*]]` syntax (normally supplied by sbt-tpolecat,
       * which we disable here). The flag was renamed `-Ykind-projector` -> `-Xkind-projector` in 3.4+.
       * `-no-indent` matches the project's brace style.
       *
       * `-deprecation` surfaces the FULL issue-#96 message (otherwise it is summarized as "there were N
       * deprecation warnings" and `-Wconf`'s `msg=` cannot match it); `-Wconf` then promotes ONLY that
       * deprecation ("...not accessible here. In Scala 3.10, this implicit will no longer be found") to a
       * hard ERROR. Without this, on 3.8.4 the bug is only a warning, so the -with test would falsely PASS
       * even when the marker companion is inaccessible (on the 3.10 nightly it is already a resolution
       * error). This is what makes the guard actually fail when the fix is missing.
       */
      scalacOptions := List("-no-indent", "-deprecation", "-Wconf:msg=not accessible here:e") ++ (
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((3, minor)) if minor <= 3 => List("-Ykind-projector")
          case _ => List("-Xkind-projector")
        }
      ),
      resolvers += nonAccessibleInstanceProps.ScalaNightlyResolver,
      libraryDependencies ++= libs.tests.hedgehog.value,
    )

/** core: markers (Compile) + instances (Test), cats as % Optional. Optional is compile-scope and
  * NON-propagating, so the -without sibling's typer does not get cats from this module's classpath —
  * exactly mirroring orphanCats (cats % Optional) + its test instances.
  */
lazy val orphanCatsNonAccessibleInstanceCore = nonAccessibleInstanceModule("orphan-cats-non-accessible-instance-core")
  .settings(
    libraryDependencies += libs.cats.value % Optional,
    Compile / unmanagedSourceDirectories ++= List(
      file("modules") / "orphan-cats" / "shared" / "src" / "main" / "scala-3" / "orphan",
      file("modules") / "orphan-cats" / "shared" / "src" / "main" / "scala" / "orphan",
    ),
    Test / unmanagedSourceDirectories ++= List(
      file("modules") / "orphan-cats" / "shared" / "src" / "test" / "scala-3" / "orphan_instance",
    ),
  )

lazy val orphanCatsNonAccessibleInstanceWith = nonAccessibleInstanceModule("orphan-cats-non-accessible-instance-with")
  .dependsOn(orphanCatsNonAccessibleInstanceCore % props.IncludeTest)
  .settings(
    libraryDependencies += libs.cats.value % Test, // cats present so the with-specs resolve & run
    Test / unmanagedSourceDirectories ++= List(
      file("modules") / "orphan-cats-test-with-cats" / "shared" / "src" / "test" / "scala",
    ),
  )

lazy val orphanCatsNonAccessibleInstanceWithout =
  nonAccessibleInstanceModule("orphan-cats-non-accessible-instance-without")
    .dependsOn(orphanCatsNonAccessibleInstanceCore % props.IncludeTest)
    .settings(
      // cats genuinely absent here => the marker's cats.Monad return type fails to load =>
      // typeCheckErrors sees @implicitNotFound (the degradation contract).
      excludeDependencies += ExclusionRule(organization = "org.typelevel"),
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-cats-test-without-cats" / "shared" / "src" / "test" / "scala-3",
      ),
    )

// circe core: markers (Compile, circe % Optional) + instances (Test). circe instances live in the
// orphan-circe-test module's MAIN scope; here they are placed in Test so the with/without siblings
// consume them via test->test (these modules are test-only).
lazy val orphanCirceNonAccessibleInstanceCore =
  nonAccessibleInstanceModule("orphan-circe-non-accessible-instance-core")
    .settings(
      // core (markers) needs circe-core; the instances additionally use circe-generic's semiauto derivation.
      libraryDependencies ++= List(
        libs.circeCore.value    % Optional,
        libs.circeGeneric.value % Optional,
      ),
      Compile / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-circe" / "shared" / "src" / "main" / "scala-3" / "orphan",
        file("modules") / "orphan-circe" / "shared" / "src" / "main" / "scala" / "orphan",
      ),
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-circe-test" / "shared" / "src" / "main" / "scala-3" / "orphan_instance",
      ),
    )

lazy val orphanCirceNonAccessibleInstanceWith =
  nonAccessibleInstanceModule("orphan-circe-non-accessible-instance-with")
    .dependsOn(orphanCirceNonAccessibleInstanceCore % props.IncludeTest)
    .settings(
      libraryDependencies ++= List(
        libs.circeCore.value    % Test,
        libs.circeGeneric.value % Test,
        libs.circeLiteral.value % Test,
        libs.circeParser.value  % Test,
        libs.circeJawn.value    % Test,
      ),
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-circe-test-with-circe" / "shared" / "src" / "test" / "scala",
      ),
    )

lazy val orphanCirceNonAccessibleInstanceWithout =
  nonAccessibleInstanceModule("orphan-circe-non-accessible-instance-without")
    .dependsOn(orphanCirceNonAccessibleInstanceCore % props.IncludeTest)
    .settings(
      excludeDependencies += ExclusionRule(organization = "io.circe"),
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-circe-test-without-circe" / "shared" / "src" / "test" / "scala-3",
      ),
    )

// spray-json core: markers (Compile, spray-json % Optional) + instances (Test).
lazy val orphanSprayJsonNonAccessibleInstanceCore =
  nonAccessibleInstanceModule("orphan-spray-json-non-accessible-instance-core")
    .settings(
      libraryDependencies += libs.sprayJson % Optional,
      Compile / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-spray-json" / "shared" / "src" / "main" / "scala-3" / "orphan",
        file("modules") / "orphan-spray-json" / "shared" / "src" / "main" / "scala" / "orphan",
      ),
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-spray-json-test" / "shared" / "src" / "main" / "scala-3" / "orphan_instance",
      ),
    )

lazy val orphanSprayJsonNonAccessibleInstanceWith =
  nonAccessibleInstanceModule("orphan-spray-json-non-accessible-instance-with")
    .dependsOn(orphanSprayJsonNonAccessibleInstanceCore % props.IncludeTest)
    .settings(
      libraryDependencies += libs.sprayJson % Test,
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-spray-json-test-with-spray-json" / "shared" / "src" / "test" / "scala",
      ),
    )

lazy val orphanSprayJsonNonAccessibleInstanceWithout =
  nonAccessibleInstanceModule("orphan-spray-json-non-accessible-instance-without")
    .dependsOn(orphanSprayJsonNonAccessibleInstanceCore % props.IncludeTest)
    .settings(
      excludeDependencies += ExclusionRule(organization = "io.spray"),
      Test / unmanagedSourceDirectories ++= List(
        file("modules") / "orphan-spray-json-test-without-spray-json" / "shared" / "src" / "test" / "scala-3",
      ),
    )

// Runs ONLY the issue-#96 (non-accessible instance) tests. Cross-build with `+`, e.g.
//   sbt "++3.8.4!" runNonAccessibleInstanceTests
addCommandAlias(
  "runNonAccessibleInstanceTests",
  Seq(
    "orphan-cats-non-accessible-instance-with/test",
    "orphan-cats-non-accessible-instance-without/test",
    "orphan-circe-non-accessible-instance-with/test",
    "orphan-circe-non-accessible-instance-without/test",
    "orphan-spray-json-non-accessible-instance-with/test",
    "orphan-spray-json-non-accessible-instance-without/test",
  ).mkString(";", ";", ""),
)
addCommandAlias(
  "cleanNonAccessibleInstanceTests",
  Seq(
    "orphan-cats-non-accessible-instance-core/clean",
    "orphan-cats-non-accessible-instance-with/clean",
    "orphan-cats-non-accessible-instance-without/clean",
    "orphan-circe-non-accessible-instance-core/clean",
    "orphan-circe-non-accessible-instance-with/clean",
    "orphan-circe-non-accessible-instance-without/clean",
    "orphan-spray-json-non-accessible-instance-core/clean",
    "orphan-spray-json-non-accessible-instance-with/clean",
    "orphan-spray-json-non-accessible-instance-without/clean",
  ).mkString(";", ";", ""),
)

lazy val props =
  new {

    private val GitHubRepo = findRepoOrgAndName

    val Org = "io.kevinlee"

    val GitHubUsername = GitHubRepo.fold("kevin-lee")(_.orgToString)
    val RepoName       = GitHubRepo.fold("orphan")(_.nameToString)

    val Scala3Version      = "3.3.3"
    val Scala2Version      = "2.13.16"
    val CrossScalaVersions = Seq(Scala2Version, "2.12.18", Scala3Version)

    val ProjectScalaVersion = Scala2Version
//    val ProjectScalaVersion = Scala3Version

    lazy val licenses = List(License.MIT)

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    val IncludeTest = "compile->compile;test->test"

    val XuweiKScalafixRulesVersion = "0.6.12"

    val HedgehogVersion = "0.13.0"

    val ExtrasVersion = "0.49.0"

    val CatsVersion = "2.12.0"

    val ScalacCompatAnnotationVersion = "0.1.4"

    val CirceVersion = "0.14.12"

    val SprayJsonVersion = "1.3.6"

  }

lazy val libs = new {

  lazy val cats =
    Def.setting("org.typelevel" %%% "cats-core" % props.CatsVersion)

  lazy val scalacCompatAnnotation = "org.typelevel" %% "scalac-compat-annotation" % props.ScalacCompatAnnotationVersion

  lazy val circeCore    = Def.setting("io.circe" %%% "circe-core" % props.CirceVersion)
  lazy val circeGeneric = Def.setting("io.circe" %%% "circe-generic" % props.CirceVersion)
  lazy val circeLiteral = Def.setting("io.circe" %%% "circe-literal" % props.CirceVersion)
  lazy val circeParser  = Def.setting("io.circe" %%% "circe-parser" % props.CirceVersion)
  lazy val circeJawn    = Def.setting("io.circe" %%% "circe-jawn" % props.CirceVersion)

  lazy val sprayJson = "io.spray" %% "spray-json" % props.SprayJsonVersion

  lazy val tests = new {

    lazy val hedgehog = Def.setting {
      List(
        "qa.hedgehog" %%% "hedgehog-core"   % props.HedgehogVersion,
        "qa.hedgehog" %%% "hedgehog-runner" % props.HedgehogVersion,
        "qa.hedgehog" %%% "hedgehog-sbt"    % props.HedgehogVersion,
      ).map(_ % Test)
    }

    lazy val scalaReflect = Def.setting {
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % Test
    }

    lazy val extrasTestingTools = Def.setting("io.kevinlee" %%% "extras-testing-tools" % props.ExtrasVersion % Test)

  }
}

// scalafmt: off
def prefixedProjectName(name: String) =
  s"${props.RepoName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

def isScala3(scalaVersion: String): Boolean = scalaVersion.startsWith("3")

def module(projectName: String, crossProject: CrossProject.Builder): CrossProject = {
  val prefixedName = prefixedProjectName(projectName)
  crossProject
    .in(file(s"modules/$prefixedName"))
    .settings(
      name := prefixedName,
      fork := true,
      semanticdbEnabled := true,
      scalafixConfig := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
      scalacOptions ++= (if (isScala3(scalaVersion.value)) List("-no-indent")
                         else List("-Xsource:3")),
      scalacOptions ++= {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((2, 12)) => List("-Ypartial-unification")
          case Some((2, 13)) => List.empty
          case Some((3, _)) => List("-Xprint-suspension", "-explain")
          case _ => Nil
        }
      },
//      scalacOptions ~= (ops => ops.filter(_ != "UTF-8")),
      libraryDependencies ++= libs.tests.hedgehog.value
//        ++ List(
//          libs.tests.hedgehogExtraCore.value
//        )
      ,
      wartremoverErrors ++= Warts.allBut(
        Wart.Any,
        Wart.Nothing,
        Wart.ImplicitConversion,
        Wart.ImplicitParameter
      ),
      Compile / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      Test / console / scalacOptions :=
        (console / scalacOptions)
          .value
          .filterNot(option => option.contains("wartremover") || option.contains("import")),
      /* } WartRemover and scalacOptions */
      licenses := props.licenses,
      /* coverage { */
      coverageHighlighting := (CrossVersion
        .partialVersion(scalaVersion.value) match {
        case Some((2, 10)) | Some((2, 11)) =>
          false
        case _ =>
          true
      }),
      /* } coverage */

//      scalacOptions ~= (_.filterNot(_.startsWith("-language"))),
//      scalacOptions ++= List(
//        "-language:dynamics",
//        "-language:existentials",
//        "-language:higherKinds",
//        "-language:reflectiveCalls",
//        "-language:experimental.macros",
//        "-language:implicitConversions",
//      ),
    )
}

lazy val jsCommonSettings: SettingsDefinition    = List(
  Test / fork := false,
  coverageEnabled := false,
)
lazy val jsSettingsForFuture: SettingsDefinition = List(
  Test / fork := false,
  Test / scalacOptions ++= (if (scalaVersion.value.startsWith("3")) List.empty
                            else
                              List("-P:scalajs:nowarnGlobalExecutionContext")),
  Test / compile / scalacOptions ++= (if (scalaVersion.value.startsWith("3"))
                                        List.empty
                                      else
                                        List(
                                          "-P:scalajs:nowarnGlobalExecutionContext"
                                        )),
  coverageEnabled := false,
)

lazy val nativeSettings: SettingsDefinition = List(
  Test / fork := false,
  coverageEnabled := false,
)
