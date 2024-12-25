import CommonUtils._
import kevinlee.sbt.SbtCommon._
import just.semver.SemVer
import just.semver.SemVer.{Major, Minor}

name := "skala"

organization := "io.kevinlee"

val ProjectVersion = "0.2.0"
val TheScalaVersion = "2.13.3"

val hedgehogVersion = "0.5.1"

version := ProjectVersion

scalaVersion := TheScalaVersion

crossScalaVersions := Seq("2.11.12", "2.12.12", TheScalaVersion).distinct

scalacOptions := (SemVer.parseUnsafe(scalaVersion.value) match {
  case SemVer(SemVer.Major(2), SemVer.Minor(13), SemVer.Patch(patch), _, _) =>
    val options = scalacOptions.value
    if (patch >= 3)
      options.filterNot(_ == "-Xlint:nullary-override")
    else
      options
  case _: SemVer =>
    scalacOptions.value
})

enablePlugins(DevOopsGitReleasePlugin)

wartremoverErrors ++= Warts.allBut(Wart.Overloading)

coverageMinimum := 90

coverageFailOnMinimum := true

coverageHighlighting := true

Test / publishArtifact := false

Test / parallelExecution := false

addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

Compile / unmanagedSourceDirectories ++= {
  val sharedSourceDir = baseDirectory.value / "src/main"
  if (scalaVersion.value.startsWith("2.13"))
    Seq(sharedSourceDir / "scala-2.12_2.13")
  else if (scalaVersion.value.startsWith("2.12"))
    Seq(sharedSourceDir / "scala-2.12_2.13", sharedSourceDir / "scala-2.11_2.12")
  else if (scalaVersion.value.startsWith("2.11"))
    Seq(sharedSourceDir / "scala-2.11_2.12")
  else
    Seq.empty
}

Test / unmanagedSourceDirectories ++= {
  val sharedSourceDir = baseDirectory.value / "src/main"
  if (scalaVersion.value.startsWith("2.13"))
    Seq(sharedSourceDir / "scala-2.12_2.13")
  else if (scalaVersion.value.startsWith("2.12"))
    Seq(sharedSourceDir / "scala-2.12_2.13", sharedSourceDir / "scala-2.11_2.12")
  else if (scalaVersion.value.startsWith("2.11"))
    Seq(sharedSourceDir / "scala-2.11_2.12")
  else
    Seq.empty
}

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test,

  "org.scalamock" %% "scalamock" % "4.4.0" % Test,

  "com.storm-enroute" %% "scalameter" % "0.19" % Test
) ++ (Seq(
    "qa.hedgehog" %% "hedgehog-core" % hedgehogVersion,
    "qa.hedgehog" %% "hedgehog-runner" % hedgehogVersion,
    "qa.hedgehog" %% "hedgehog-sbt" % hedgehogVersion
  ).map(_ % Test))


Compile / doc / scalacOptions ++= Seq(
  "-no-link-warnings" // To ignore Scaladoc error saying "Could not find any member to link for ..."
)

/* Performance Test { */
testFrameworks += TestFramework("org.scalameter.ScalaMeterFramework")
testFrameworks += TestFramework("hedgehog.sbt.Framework")

Test / parallelExecution := false

/* } Performance Test */

publishMavenStyle := true

pomIncludeRepository := { _ => false }

pomExtra := <url>https://github.com/Kevin-Lee/skala</url>
              <licenses>
                <license>
                  <name>The MIT License</name>
                  <url>https://github.com/Kevin-Lee/skala/blob/master/LICENSE</url>
                </license>
              </licenses>
              <scm>
                <url>git@github.com:Kevin-Lee/skala.git</url>
                <connection>scm:git:git@github.com:Kevin-Lee/skala.git</connection>
              </scm>

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))


lazy val writeVersion = inputKey[Unit]("Write Version in File'")

writeVersion := versionWriter(() => Def.spaceDelimited("filename").parsed)(ProjectVersion)


import org.scoverage.coveralls.Imports.CoverallsKeys._

coverallsTokenFile := Option(s"""${Path.userHome.absolutePath}/.coveralls-credentials""")
