organization := "dev.vgerasimov"
version := "0.2.0"
scalaVersion := "2.13.4"

idePackagePrefix := Some("dev.vgerasimov.shapelse")

resolvers ++= Seq(
  "Typesafe".at("https://repo.typesafe.com/typesafe/releases/")
)

libraryDependencies ++= Seq(
  "com.chuusai"   %% "shapeless" % "2.4.0-M1",
  "org.scalatest" %% "scalatest" % "3.2.2" % "test"
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
    name := "shapelse",
  )

testOptions += Tests.Argument(
  framework = Some(TestFrameworks.ScalaTest),
  args = List("-oSD")
)

scalacOptions in (Compile, doc) ++= Seq(
  "-groups"
)

githubOwner := "wlad031"
githubActor := "wlad031"
githubRepository := "shapelse"
