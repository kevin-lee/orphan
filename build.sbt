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
    orphanCatsTestWithoutCatsJvm,
    orphanCatsTestWithoutCatsJs,
    orphanCatsTestWithCatsJvm,
    orphanCatsTestWithCatsJs,
  )

lazy val orphanCats    = module("cats", crossProject(JVMPlatform, JSPlatform))
  .settings(
    libraryDependencies ++= List(libs.cats.value % Optional) ++ (
      if (isScala3(scalaVersion.value)) List.empty
      else
        List(
          libs.scalacCompatAnnotation,
          libs.tests.scalaReflect.value
        )
    ),
  )
lazy val orphanCatsJvm = orphanCats.jvm
lazy val orphanCatsJs  = orphanCats.js.settings(jsCommonSettings)

lazy val orphanCatsTestWithoutCats    = module("cats-test-without-cats", crossProject(JVMPlatform, JSPlatform))
  .settings(noPublish)
  .settings(
    libraryDependencies ++= List(libs.tests.extrasTestingTools.value),
  )
  .dependsOn(orphanCats % props.IncludeTest)
lazy val orphanCatsTestWithoutCatsJvm = orphanCatsTestWithoutCats.jvm
lazy val orphanCatsTestWithoutCatsJs  = orphanCatsTestWithoutCats.js.settings(jsCommonSettings)

lazy val orphanCatsTestWithCats    = module("cats-test-with-cats", crossProject(JVMPlatform, JSPlatform))
  .settings(noPublish)
  .settings(
    libraryDependencies ++= List(libs.cats.value % Test),
  )
  .dependsOn(orphanCats % props.IncludeTest)
lazy val orphanCatsTestWithCatsJvm = orphanCatsTestWithCats.jvm
lazy val orphanCatsTestWithCatsJs  = orphanCatsTestWithCats.js.settings(jsCommonSettings)

lazy val props =
  new {

    private val GitHubRepo = findRepoOrgAndName

    val Org = "io.kevinlee"

    val GitHubUsername = GitHubRepo.fold("kevin-lee")(_.orgToString)
    val RepoName       = GitHubRepo.fold("orphan")(_.nameToString)

    val Scala3Version      = "3.1.3"
    val Scala2Version      = "2.13.13"
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

    val HedgehogVersion = "0.10.1"

    val ExtrasVersion = "0.47.0"

    val CatsVersion = "2.8.0"

    val ScalacCompatAnnotationVersion = "0.1.4"

  }

lazy val libs = new {

  lazy val cats =
    Def.setting("org.typelevel" %%% "cats-core" % props.CatsVersion)

  lazy val scalacCompatAnnotation = "org.typelevel" %% "scalac-compat-annotation" % props.ScalacCompatAnnotationVersion

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
