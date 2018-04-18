logLevel := Level.Warn

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.2.1")

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.2")

resolvers ++= Seq(
  "Github-API" at "http://repo.jenkins-ci.org/public/",
  "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"
)

addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.3.0")
