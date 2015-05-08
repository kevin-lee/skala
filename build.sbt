name := "skala"

version := "0.0.1"

scalaVersion := "2.11.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

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
