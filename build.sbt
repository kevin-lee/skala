import CommonUtils._

name := "skala"

organization := "io.kevinlee"

val projectVersion = "0.0.5"

version := projectVersion

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-deprecation",             // Emit warning and location for usages of deprecated APIs.
  "-feature",                 // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",               // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings",         // Fail the compilation if there are any warnings.
//  "-Xlint",                 // Enable recommended additional warnings.
  "-Ywarn-adapted-args",      // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code",         // Warn when dead code is identified.
  "-Ywarn-inaccessible",      // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override",  // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen"      // Warn when numerics are widened.
)

libraryDependencies ++= Seq(
//  "org.scala-lang" % "scala-compiler" % "2.11.8",
//  "org.scala-lang" % "scala-library" % "2.11.8",
//  "org.scala-lang" % "scala-reflect" % "2.11.8",
//  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
//  "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4",

  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "com.storm-enroute" %% "scalameter" % "0.6",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
)

scalacOptions in (Compile, doc) ++= Seq(
  "-no-link-warnings" // To ignore Scaladoc error saying "Could not find any member to link for ..."
)

/* Performance Test { */
testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false

/* } Performance Test */

publishMavenStyle := true

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>https://github.com/Kevin-Lee/skala</url>
    <licenses>
      <license>
        <name>The MIT License</name>
        <url>https://github.com/Kevin-Lee/skala/blob/master/LICENSE</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:Kevin-Lee/skala.git</url>
      <connection>scm:git:git@github.com:Kevin-Lee/skala.git</connection>
    </scm>)

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))


lazy val writeVersion = inputKey[Unit]("Write Version in File'")

writeVersion := versionWriter(() => Def.spaceDelimited("filename").parsed)(projectVersion)


import org.scoverage.coveralls.Imports.CoverallsKeys._

coverallsTokenFile := Option(s"""${Path.userHome.absolutePath}/.coveralls-credentials""")


val repoLocation = "Kevin-Lee/skala"

/* GitHub Release { */
GithubRelease.repo := repoLocation

GithubRelease.tag := s"v$projectVersion"

GithubRelease.releaseName := GithubRelease.tag.value

GithubRelease.commitish := "release"

GithubRelease.notesFile := GithubRelease.notesDir.value / s"${projectVersion}.md"

GithubRelease.releaseAssets := {

  val binNames = listFiles(target.value / "ci", "*.jar")

  println(s"fileNames: $binNames")

  binNames
}
/* } GitHub Release */
