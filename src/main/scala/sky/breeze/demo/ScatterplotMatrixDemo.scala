package sky.breeze.demo

import breeze.linalg._
import breeze.plot._
import sky.breeze.{HWData, ScatterplotMatrix}

object ScatterplotMatrixDemo extends App {
  val DataDirectory = "data/"
  val fileName = "Davis.csv"
  val data = HWData.load(DataDirectory + fileName)
  val fig = Figure("Scatterplot matrix demo")
  val m = new ScatterplotMatrix(fig)

  // Make a matrix with three columns: the height, weight and
  // reported weight data.
  val featureMatrix = DenseMatrix.horzcat(
    data.heights.toDenseMatrix.t,
    data.weights.toDenseMatrix.t,
    data.reportedWeights.toDenseMatrix.t
  )
  m.plotFeatures(featureMatrix, List("height", "weight", "reportedWeights"))

  fig.saveas("scatterplot_demo.png")
}