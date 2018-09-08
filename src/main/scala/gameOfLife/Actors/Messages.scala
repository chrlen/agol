package gameOfLife.Actors

import scala.util.Random
import breeze.linalg._

object BaseMessage{
  implicit def date2timestamp(date: java.util.Date):java.sql.Timestamp =
    new java.sql.Timestamp(date.getTime)
}

abstract class BaseMessage(){
  val timeStamp = new java.util.Date()
}

object Pixel{
  private val r = scala.util.Random
  def randomTuple(x_min:Int, x_max:Int,
                  y_min:Int, y_max:Int): Tuple2[Int,Int] =  {
    (r.nextInt(x_max),r.nextInt(y_max))
  }
}
abstract class Pixel extends BaseMessage{
  def coordinates:Tuple2[Int,Int]
  def player:Int
}


case class SetMatrix[T](matrix:DenseMatrix[T])

case object SetPixel{
  val rand = scala.util.Random
  def apply(coordinates: Tuple2[Int, Int],player:Int=0): SetPixel = new SetPixel(coordinates,player)
}
case class SetPixel(coordinates:Tuple2[Int,Int],player:Int) extends Pixel

object ResultPixel{
  def apply(coordinates:Tuple2[Int,Int],player:Int=0): ResultPixel = new ResultPixel(coordinates,player)
}
case class ResultPixel(coordinates:Tuple2[Int,Int],player:Int) extends Pixel
case object Convolve{
  def apply(matrix: DenseMatrix[Int],x:Int,y:Int): Convolve = new Convolve(matrix,x,y)
}
case class Convolve(matrix:DenseMatrix[Int],x:Int,y:Int)

case class Result(value:Int,x:Int,y:Int)
case class Trigger() extends BaseMessage


