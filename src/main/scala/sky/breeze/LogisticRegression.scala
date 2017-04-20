package sky.breeze

import breeze.linalg._
import breeze.numerics._
import breeze.optimize._

class LogisticRegression(
                          val training: DenseMatrix[Double],
                          val target: DenseVector[Double]
                        ) {
  lazy val optimalCoefficients: DenseVector[Double] = calculateOptimalCoefficients

  def predict(features: DenseVector[Double]): Double = {
    val xBeta = optimalCoefficients dot features
    sigmoid(xBeta)
  }

  def predictProbabilitiesMany(test:DenseMatrix[Double]):DenseVector[Double] = {
    val xBeta = test * optimalCoefficients
    sigmoid(xBeta)
  }

  def classifyMany(test:DenseMatrix[Double]):DenseVector[Double] = {
    val probabilities = predictProbabilitiesMany(test)
    I((probabilities >:> 0.5).toDenseVector)
  }

  private def calculateOptimalCoefficients: DenseVector[Double] = {
    val f = new DiffFunction[DenseVector[Double]] {
      def calculate(parameters: DenseVector[Double]) =
        costFunctionAndGradient(parameters)
    }

    minimize(f, DenseVector.zeros[Double](training.cols))
  }

  def costFunctionAndGradient(coefficients: DenseVector[Double])
  : (Double, DenseVector[Double]) = {
    val xBeta = training * coefficients
    val expXBeta = exp(xBeta)
    val cost = -sum((target *:* xBeta) - log1p(expXBeta))
    val probs = sigmoid(xBeta)
    val grad = training.t * (probs - target)
    (cost, grad)
  }

}
