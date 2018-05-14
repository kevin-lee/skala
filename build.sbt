import CommonUtils._

name := "skala"

organization := "io.kevinlee"

val ProjectVersion = "0.0.10"
val TheScalaVersion = "2.12.4"

version := ProjectVersion

scalaVersion := TheScalaVersion

crossScalaVersions := Seq("2.11.12", "2.12.4")

scalacOptions ++= Seq(
  "-deprecation",             // Emit warning and location for usages of deprecated APIs.
  "-feature",                 // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",               // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings",         // Fail the compilation if there are any warnings.
  "-Xlint",                 // Enable recommended additional warnings.
  "-Ywarn-adapted-args",      // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code",         // Warn when dead code is identified.
  "-Ywarn-inaccessible",      // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override",  // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen"      // Warn when numerics are widened.
)

wartremoverErrors ++= Warts.allBut(Wart.Overloading)

coverageMinimum := 80

coverageFailOnMinimum := true

coverageHighlighting := true

publishArtifact in Test := false

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.scalacheck" %% "scalacheck" % "1.13.4" % Test,

  "org.scalamock" %% "scalamock" % "4.1.0" % Test,

  "com.storm-enroute" %% "scalameter" % "0.9" % Test
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


lazy val ghreleaseGithubOrigin  = settingKey[Option[Origin]]("GitHub origin")

ghreleaseGithubOrigin := githubOrigin(baseDirectory.value)

/* GitHub Release { */
ghreleaseRepoOrg :=
  ghreleaseGithubOrigin.value.map(_.organization)
                             .getOrElse(throw new RuntimeException("No Repo organization (user) name found"))

ghreleaseRepoName :=
  ghreleaseGithubOrigin.value.map(_.name)
                             .getOrElse(throw new RuntimeException("No Repo name found"))

ghreleaseNotes := { tagName =>
  val ver = tagName.stripPrefix("v")
  IO.read(baseDirectory.value / "notes" / s"$ver.md")
}

ghreleaseTitle := { tagName => s"${name.value} $tagName" }

ghreleaseAssets := {
  lazy val nameFilter = wildcardFilter("*.jar")
  val assets = packagedArtifacts.value.values.toSeq.filter(nameFilter.accept)
  println(s">>> Assets to release: ${assets.mkString("\n")}")
  assets
}

/* } GitHub Release */
