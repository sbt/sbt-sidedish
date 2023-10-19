ThisBuild / version := {
  val orig = (ThisBuild / version).value
  if (orig.endsWith("-SNAPSHOT")) "0.2.1-SNAPSHOT"
  else orig
}
ThisBuild / organization := "com.eed3si9n"
ThisBuild / dynverSonatypeSnapshots := true
ThisBuild / description := "a tool to write plugin that downloads and runs an app"
ThisBuild / homepage := Some(url("https://github.com/sbt/sbt-sidedish"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/sbt/sbt-sidedish"),
    "git@github.com:sbt/sbt-sidedish.git"
  )
)
ThisBuild / licenses := Seq(
  "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")
)

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-sidedish"
  )
