package sky

import org.saddle.io.{CsvFile, CsvParser}
import org.saddle.{Mat, Vec, vec}

object SDData {

  def load(s: String): SDData = {
    val file = CsvFile(s)
    val frame = CsvParser.parse()(file).withRowIndex(0).withColIndex(0)

    val genders = frame.col("sex").mapValues(_.replace("\"", "").head).colAt(0).values
    val weights = frame.col("weight").mapValues(CsvParser.parseDouble).colAt(0).values
    val heights = frame.col("height").mapValues(CsvParser.parseDouble).colAt(0).values
    val reportedWeights = frame.col("repwt").mapValues(CsvParser.parseDouble).colAt(0).values
    val reportedHeights = frame.col("repht").mapValues(CsvParser.parseDouble).colAt(0).values

    SDData(weights, heights, reportedWeights, reportedHeights, genders)
  }
}

case class SDData(
                   weights: Vec[Double],
                   heights: Vec[Double],
                   reportedWeights: Vec[Double],
                   reportedHeights: Vec[Double],
                   genders: Vec[Char]
                 ) {

  lazy val rescaledHeights: Vec[Double] = (heights - heights.mean) / heights.stdev
  require(weights.length == npoints)
  require(reportedWeights.length == npoints)
  require(genders.length == npoints)
  require(reportedHeights.length == npoints)
  lazy val rescaledWeights: Vec[Double] = (weights - weights.mean) / weights.stdev
  lazy val featureMatrix: Mat[Double] = Mat(vec.ones(npoints), rescaledHeights, rescaledWeights)
  lazy val target: Vec[Double] = genders.values.map { gender => if (gender == 'M') 1.0 else 0.0 }
  val npoints:Int = heights.length

  override def toString: String = s"HWData [ $npoints rows ]"

}
