package sky.saddle

import breeze.plot._
import org.saddle.Mat
import sky.breeze.ScatterplotMatrix

object SScatterplotMatrixDemo{
  implicit val filePath = "data/Davis.csv"

  def main(args:Array[String]): Unit ={
    val data = SDData.load
    val fig = Figure("Scatterplot matrix demo")
    val m = new ScatterplotMatrix(fig)
    // Make a matrix with three columns: the height, weight and
    // reported weight data.
    val featureMatrix: Mat[Double] = Mat(
      data.heights,
      data.weights,
      data.reportedWeights
    )

    import sky.utils.ImplicitConversion._
    m.plotFeatures(featureMatrix, List("height", "weight", "reportedWeights"))

    fig.saveas("scatterplot_demo.png")
  }
}