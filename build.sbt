organization := "dev.vgerasimov"
version := "0.3.0"
scalaVersion := "2.13.5"

idePackagePrefix := Some("dev.vgerasimov.shapelse")

resolvers ++= Seq(
  "Typesafe".at("https://repo.typesafe.com/typesafe/releases/")
)

libraryDependencies ++= Seq(
  "org.scala-lang"             % "scala-reflect"              % scalaVersion.value,
  "com.chuusai"                %% "shapeless"                 % "2.4.0-M1",
  "org.scalatest"              %% "scalatest"                 % "3.2.7" % "test",
  "org.scalatestplus"          %% "scalacheck-1-15"           % "3.2.5.0" % "test",
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5"
)

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-Xlint:implicit-recursion",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "shapelse"
  )

testOptions += Tests.Argument(
  framework = Some(TestFrameworks.ScalaTest),
  args = List("-oSD")
)

Compile / sources := Seq.empty
Compile / publishArtifact := false

packageDoc / publishArtifact := false

doc / sources := Seq.empty

githubOwner := "wlad031"
githubActor := "wlad031"
githubRepository := "shapelse"
