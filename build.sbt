import CommonUtils._

name := "skala"

organization := "cc.kevinlee"

val projectVersion = "0.0.3"

version := projectVersion

scalaVersion := "2.11.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

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

import bintray.Keys._

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

Seq(bintrayPublishSettings:_*)

repository in bintray := "maven"



lazy val writeVersion = inputKey[Unit]("Write Version in File'")

writeVersion := versionWriter(() => Def.spaceDelimited("filename").parsed)(projectVersion)


import org.scoverage.coveralls.Imports.CoverallsKeys._

coverallsTokenFile := Option(s"""${sys.props("user.home")}/.coveralls-credentials""")


val repoLocation = "Kevin-Lee/skala"

/* GitHub Release { */
GithubRelease.repo := repoLocation

GithubRelease.tag := s"v${projectVersion}"

GithubRelease.releaseName := GithubRelease.tag.value

GithubRelease.commitish := "release"

GithubRelease.notesFile := GithubRelease.notesDir.value / s"${projectVersion}.md"

GithubRelease.releaseAssets := {

  val binNames = listFiles(target.value / "ci", "*.jar")

  println(s"fileNames: $binNames")

  binNames
}
/* } GitHub Release */
