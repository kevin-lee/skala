import CommonUtils._

name := "skala"

organization := "io.kevinlee"

val ProjectVersion = "0.2.0"
val TheScalaVersion = "2.12.6"

version := ProjectVersion

scalaVersion := TheScalaVersion

crossScalaVersions := Seq("2.11.12", TheScalaVersion)

def crossScalacOptions(
  commonOptions: Seq[String],
  current: Seq[String],
  scalaVersion: String)(
  versionSpecific: PartialFunction[Option[(Long, Long)], Seq[String]]
): Seq[String] =
  commonOptions ++
  current ++
  versionSpecific(CrossVersion.partialVersion(scalaVersion))

scalacOptions := crossScalacOptions(
  Seq(
    "-deprecation",             // Emit warning and location for usages of deprecated APIs.
    "-feature",                 // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked",               // Enable additional warnings where generated code depends on assumptions.
    "-Xfatal-warnings",         // Fail the compilation if there are any warnings.
    "-Xlint",                   // Enable recommended additional warnings.
    "-Ywarn-adapted-args",      // Warn if an argument list is modified to match the receiver.
    "-Ywarn-dead-code",         // Warn when dead code is identified.
    "-Ywarn-inaccessible",      // Warn about inaccessible types in method signatures.
    "-Ywarn-nullary-override",  // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
    "-Xlint:nullary-unit",      // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen"      // Warn when numerics are widened.
  ),
  scalacOptions.value,
  scalaVersion.value
) {
  case Some((2, 12)) =>
    Seq(
      "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
      "-Ywarn-unused:imports",   // Warn if an import selector is not referenced.
      "-Ywarn-unused:locals",    // Warn if a local definition is unused.
      "-Ywarn-unused:params"     // Warn if a value parameter is unused.
    )
  case _ =>
    Nil
}

wartremoverErrors ++= Warts.allBut(Wart.Overloading)

coverageMinimum := 90

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



/* GitHub Release { */
ghreleaseGithubOrigin := githubOrigin(baseDirectory.value)

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
  val assets = listFiles(target.value / "ci", "*.jar")

  println(
    s"""
       |>>> Assets to release:
       |----------------------
       |  ${assets.mkString("\n  ")}
       |""".stripMargin)

  assets

}

/* } GitHub Release */
