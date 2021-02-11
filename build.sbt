ThisBuild / organization := "dev.vgerasimov"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.4"

ThisBuild / idePackagePrefix := Some("dev.vgerasimov.scmc")

ThisBuild / resolvers ++= Seq(
  "Typesafe".at("https://repo.typesafe.com/typesafe/releases/"),
)

ThisBuild / libraryDependencies ++= Seq(
  Seq(
    "com.chuusai" %% "shapeless" % "2.4.0-M1"
  ),
  Seq(
    "org.scalatest" %% "scalatest" % "3.2.2" % "test"
  ),
).flatten

ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-Xlint:implicit-recursion",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-class-metadata-collector",
    assemblyJarName in assembly := "scala-class-metadata-collector.jar"
  )

Test / testOptions += Tests.Argument(
  framework = Some(TestFrameworks.ScalaTest),
  args = List("-oSD")
)

scalacOptions in (Compile, doc) ++= Seq(
  "-groups"
)

githubOwner := "wlad031"
githubActor := "wlad031"
githubRepository := "scala-class-metadata-collector"
