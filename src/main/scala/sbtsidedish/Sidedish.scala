package sbtsidedish

import sbt._
import Keys._

case class Sidedish(id: String, location: File, scalaVersion0: String,
  modules: Seq[ModuleID], mainClass: String) {

  private val publishSigned = taskKey[Unit]("Publishing all artifacts, but SIGNED using PGP.")

  def project: Project =
    Project(id, location).
      settings(
        scalaVersion := scalaVersion0,
        libraryDependencies ++= modules,
        publish := {},
        publishLocal := {},
        publishSigned := {}
      )

  val projectRef: ProjectReference = LocalProject(id)
  def runTask(args: Seq[String]): Def.Initialize[Task[Unit]] =
    Def.task {
      val r = (runner in (projectRef, Compile, run)).value
      val cp = (fullClasspath in (projectRef, Compile, run)).value
      r.run(mainClass, Attributed.data(cp), args, streams.value.log)
      ()
    }

  def forkRunTask(workingDirectory: File, jvmOptions: Seq[String], args: Seq[String]): Def.Initialize[Task[Unit]] =
    Def.task {
      val fo = ForkOptions(
        javaHome = (javaHome in projectRef).value,
        outputStrategy = (outputStrategy in projectRef).value,
        bootJars = Vector.empty,
        workingDirectory = Option(workingDirectory),
        runJVMOptions = jvmOptions.toVector,
        connectInput = (connectInput in projectRef).value,
        envVars = (envVars in projectRef).value
      )
      val r = new ForkRun(fo)
      (runner in (projectRef, Compile, run)).value
      val cp = (fullClasspath in (projectRef, Compile, run)).value
      r.run(mainClass, Attributed.data(cp), args, streams.value.log)
      ()
    }
}
