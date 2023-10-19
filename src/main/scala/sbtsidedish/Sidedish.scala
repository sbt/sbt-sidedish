package sbtsidedish

import sbt._
import Keys._

case class Sidedish(
    id: String,
    location: File,
    scalaVersion0: String,
    modules: Seq[ModuleID],
    mainClass: String
) {

  private val publishSigned =
    taskKey[Unit]("Publishing all artifacts, but SIGNED using PGP.")

  def project: Project =
    Project(id, location).settings(
      scalaVersion := scalaVersion0,
      libraryDependencies ++= modules,
      publish := {},
      publishLocal := {},
      publishSigned := {},
      publish / skip := true
    )

  val projectRef: ProjectReference = LocalProject(id)
  def runTask(args: Seq[String]): Def.Initialize[Task[Unit]] =
    Def.task {
      val r = (projectRef / Compile / run / runner).value
      val cp = (projectRef / Compile / run / fullClasspath).value
      r.run(mainClass, Attributed.data(cp), args, streams.value.log)
      ()
    }

  def forkRunTask(
      workingDirectory: File,
      jvmOptions: Seq[String],
      args: Seq[String]
  ): Def.Initialize[Task[Unit]] =
    Def.task {
      val fo = ForkOptions(
        javaHome = (projectRef / javaHome).value,
        outputStrategy = (projectRef / outputStrategy).value,
        bootJars = Vector.empty,
        workingDirectory = Option(workingDirectory),
        runJVMOptions = jvmOptions.toVector,
        connectInput = (projectRef / connectInput).value,
        envVars = (projectRef / envVars).value
      )
      val r = new ForkRun(fo)
      (projectRef / Compile / run / runner).value
      val cp = (projectRef / Compile / run / fullClasspath).value
      r.run(mainClass, Attributed.data(cp), args, streams.value.log)
      ()
    }
}
