lazy val commonSettings = Seq(
  version in ThisBuild := "0.1.1",
  organization in ThisBuild := "com.eed3si9n"
)

lazy val root = (project in file(".")).
  settings(commonSettings).
  settings(
    sbtPlugin := true,
    publishMavenStyle := false,
    name := "sbt-sidedish",
    description := "a tool to write plugin that downloads and runs an app",
    homepage := Some(url("https://github.com/sbt/sbt-sidedish")),
    scmInfo := Some(ScmInfo(url("https://github.com/sbt/sbt-sidedish"), "git@github.com:sbt/sbt-sidedish.git")),
    licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
    bintrayOrganization in bintray := None,
    bintrayRepository := "sbt-plugins"
  )
