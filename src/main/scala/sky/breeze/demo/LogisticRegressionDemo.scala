package sky.breeze.demo

import sky.breeze.{HWData, LogisticRegression}

object LogisticRegressionDemo {
  val DataDirectory = "data/"
  val fileName = "Davis.csv"

  def main(args:Array[String]) {
    val data = HWData.load(DataDirectory + fileName)
    val regressor = new LogisticRegression(data.featureMatrix, data.target)
    val coefficients = regressor.optimalCoefficients
    println("Optimal coefficients (on re-scaled data): ")
    println(coefficients)
  }
}
