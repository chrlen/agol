package gameOfLife.Actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.Router
import breeze.linalg.DenseMatrix
import breeze.linalg.sum

abstract class Representation {
  def workerProps:Props = ???
  def insert(p:Pixel):Unit
  def iterate(router:Router):Unit
}

  object SimpleRepresentationWorker{
    def apply: SimpleRepresentationWorker = new SimpleRepresentationWorker()
    def props():Props = Props( new SimpleRepresentationWorker())
  }
class SimpleRepresentationWorker extends Actor with ActorLogging {
  override def receive: Receive = {
    case c: Convolve => {
      val res = sum(c.matrix)
      var returnVal: Int = 0
      val isLiving: Boolean = (c.matrix(1, 1) == 1)

      if (isLiving) {
        if (res < 2) sender.tell(Result(0, c.x, c.y), self)
        if (res > 3) sender.tell(Result(0, c.x, c.y), self)

      } else {
        if (res == 3) sender.tell(Result(1, c.x, c.y), self)
      }
    }
    case _ => log.info("RepWorker!!!")
  }
}
class SimpleRepresentation(x:Int = 1001, y:Int = 1001) extends Representation{
  private val matrix = DenseMatrix.zeros[Int](x,y)
  override def workerProps: Props = SimpleRepresentationWorker.props()
  override def insert(p: Pixel): Unit = {
    matrix(p.coordinates._1,p.coordinates._2) = 1
  }

  override def iterate( router: Router): Unit = {
    val x_set = Seq(0,matrix.rows,1)
    val y_set = Seq(0,matrix.cols,1)
    println(x_set)
    //val indexSet = for {x <- x_set, y <- y_set} yield (x,y)
    //indexSet.map((index) => )
  }
}