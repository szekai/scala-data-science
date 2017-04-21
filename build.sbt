name := "scalanlp"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies  ++= breeze_lib ++ dao_lib ++ Seq(

  "com.github.tototoshi" %% "scala-csv" % "latest.integration",

  "org.scala-saddle" %% "saddle-core" % "1.3.+",

  "org.slf4j" % "slf4j-nop" % "1.7.5",

  "org.scala-lang" % "scala-xml" % "latest.integration"

)

lazy val breeze_lib =  Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "0.13",

  // Native libraries are not included by default. add this if you want them (as of 0.7)
  // Native libraries greatly improve performance, but increase jar sizes.
  // It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "0.13",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "0.13"
)

lazy val dao_lib = Seq(
  "com.h2database" % "h2" % "1.4.194",

  "mysql" % "mysql-connector-java" % "5.1.35",

  "com.typesafe.slick" %% "slick" % "latest.integration"
)