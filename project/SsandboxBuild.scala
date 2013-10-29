import sbt._
import Keys._

object Resolvers {
  val typesafeReleases = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"

  val myResolvers = Seq(typesafeReleases, mavenLocal)
}

object BuildSettings {

  import Resolvers._

  val buildOrganization = "com.honnix"
  val buildVersion = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.10.3"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    shellPrompt := ShellPrompt.buildShellPrompt,
    // publish to maven repository
    // publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository"))),
    resolvers ++= myResolvers
  ) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings
}

object ShellPrompt {

  object devnull extends ProcessLogger {
    def info(s: => String) {}

    def error(s: => String) {}

    def buffer[T](f: => T): T = f
  }

  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
    )

  val buildShellPrompt = {
    (state: State) => {
      val currProject = Project.extract(state).currentProject.id
      "%s:%s:%s> ".format(
        currProject, currBranch, BuildSettings.buildVersion
      )
    }
  }
}

object Dependencies {
  // private val crossMappedDowngrade = CrossVersion.fullMapped {
  //   case "2.10.0" => "2.9.2"
  //   case x => x
  // }

  val blueprints = "com.tinkerpop.blueprints" % "blueprints-neo4j-graph" % "2.1.0"
  val swing = "org.scala-lang" % "scala-swing" % "2.10.3"
  val reflect = "org.scala-lang" % "scala-reflect" % "2.10.3"
}

object SsandboxBuild extends Build {

  import Dependencies._
  import BuildSettings._

  val commonDeps = Seq(
    blueprints,
    swing,
    reflect
  )

  lazy val RunDebug = config("debug").extend(Runtime)

  lazy val ssandbox = Project(
    id = "ssandbox",
    base = file("."),
    settings = buildSettings ++ Seq(
      libraryDependencies ++= commonDeps,
      fork in RunDebug := true,
      scalacOptions ++= Seq("-unchecked", "-language:experimental.macros"),
      javaOptions in RunDebug ++= Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005")
    ) ++ inConfig(RunDebug)(Defaults.configTasks)).configs(RunDebug)
}
