logLevel := Level.Warn

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

//resolvers += Resolver.url("sbt-scoverage repo", url("https://dl.bintray.com/sksamuel/sbt-plugins"))(Resolver.ivyStylePatterns)

//addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

resolvers ++= Seq(
  "Github-API" at "http://repo.jenkins-ci.org/public/",
  "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"
)

addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.3.0")
