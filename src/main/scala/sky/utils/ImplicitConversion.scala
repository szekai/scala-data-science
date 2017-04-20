package sky.utils

import breeze.linalg.support.LiteralRow
import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.storage.Zero
import org.saddle.{Mat, ST, Vec}

import scala.reflect.ClassTag

object ImplicitConversion {
  implicit def vec2Densevec[A:ClassTag](a: Vec[A]): DenseVector[A]={
    DenseVector(a.toSeq.toArray)
  }

  implicit def mat2DenseMat[A:ClassTag](a: Mat[A])(implicit rl: LiteralRow[IndexedSeq[A],A], zero: Zero[A], man : ClassTag[A], ev:ST[A]):DenseMatrix[A]={
    DenseMatrix(a.cols.map(_.toSeq): _*).t
  }
}
