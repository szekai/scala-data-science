package sky.saddle

import sky.breeze.LogisticRegression

object SLogisticRegressionDemo {
  val DataDirectory = "data/"
  val fileName = "Davis.csv"

  def main(args:Array[String]) {
    val data = SDData.load(DataDirectory + fileName)
    import sky.utils.ImplicitConversion._
    val regressor = new LogisticRegression(data.featureMatrix, data.target)
    val coefficients = regressor.optimalCoefficients
    println("Optimal coefficients (on re-scaled data): ")
    println(coefficients)
  }
}
