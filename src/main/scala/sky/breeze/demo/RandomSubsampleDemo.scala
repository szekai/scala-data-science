package sky.breeze.demo

import breeze.linalg.functions.manhattanDistance
import breeze.stats._
import sky.breeze.{HWData, LogisticRegression, RandomSubsample}

object RandomSubsampleDemo extends App {
  val DataDirectory = "data/"
  val fileName = "Davis.csv"
  /* Load and pre-process data */
  val data = HWData.load(DataDirectory + fileName)

  /* Cross-validation */
  val testSize = 20
  val cvCalculator = new RandomSubsample(data.npoints, testSize)

  val cvErrors = cvCalculator.mapSamples(1000) { (trainingIndices, testIndices) =>
    val regressor = new LogisticRegression(
      data.featureMatrix(trainingIndices, ::).toDenseMatrix,
      data.target(trainingIndices).toDenseVector
    )
    val genderPredictions = regressor.classifyMany(data.featureMatrix(testIndices, ::).toDenseMatrix)
    manhattanDistance(genderPredictions, data.target(testIndices)) / testSize.toDouble
  }

  println(s"Mean classification error: ${mean(cvErrors)}")
}
