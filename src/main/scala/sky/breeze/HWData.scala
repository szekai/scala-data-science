package sky.breeze

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.stats._
import com.github.tototoshi.csv.CSVReader

import scala.io.Source
import scala.reflect.ClassTag

object HWData {
  val DataDirectory = "data/"
  val fileName = "Davis.csv"

  def fromList[T:ClassTag](data: List[Map[String,String]], col:String, converter:(String => T)): DenseVector[T] ={
    DenseVector(data.map(x => converter(x(col))).toArray)
  }

  def load(file:String): HWData ={
    val reader = CSVReader.open(Source.fromFile(file))
    val data = reader.allWithHeaders()
    reader.close()

    def toDouble(s:String): Double = {
      import scala.util.Try
      Try(s.toDouble).toOption getOrElse Double.NaN
    }
    val genders = fromList(data, "sex", _.replace("\"", "").head)
    val weights = fromList(data, "weight", _.toDouble)
    val heights = fromList(data, "height", _.toDouble)
    val reportedWeights = fromList(data, "repwt", toDouble(_))
    val reportedHeights = fromList(data, "repht", toDouble(_))

    HWData(weights, heights, reportedWeights, reportedHeights, genders)
  }

  def main(args:Array[String]): Unit ={
      print(load(DataDirectory + fileName))
  }
}

case class HWData(
              weights:DenseVector[Double],
              heights:DenseVector[Double],
              reportedWeights:DenseVector[Double],
              reportedHeights:DenseVector[Double],
              genders:DenseVector[Char]
            ) {

  val npoints = heights.length
  require(weights.length == npoints)
  require(reportedWeights.length == npoints)
  require(genders.length == npoints)
  require(reportedHeights.length == npoints)

  lazy val rescaledHeights:DenseVector[Double] =
    (heights - mean(heights)) / stddev(heights)

  lazy val rescaledWeights:DenseVector[Double] =
    (weights - mean(weights)) / stddev(weights)

  lazy val featureMatrix:DenseMatrix[Double] =
    DenseMatrix.horzcat(
      DenseMatrix.ones[Double](npoints, 1),
      rescaledHeights.toDenseMatrix.t,
      rescaledWeights.toDenseMatrix.t
    )

  lazy val target:DenseVector[Double] =
    genders.values.map { gender => if(gender == 'M') 1.0 else 0.0 }

  override def toString:String = s"HWData [ $npoints rows ]"

}
