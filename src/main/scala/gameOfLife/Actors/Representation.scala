package gameOfLife.Actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.Router
import breeze.linalg.DenseMatrix
import breeze.linalg.sum

abstract class Representation {
  def workerProps:Props = ???
  def insert(p:Pixel):Unit
  def iterate(router:Router,sender:ActorRef):Unit
  def set(representationSettings:RepresentationSettings):Unit
}
abstract class RepresentationSettings {

}

object SimpleRepresentationWorker{
    def apply: SimpleRepresentationWorker = new SimpleRepresentationWorker()
    def props():Props = Props( new SimpleRepresentationWorker())
  }
class SimpleRepresentationWorker extends Actor with ActorLogging {
  override def receive: Receive = {
    case c: Convolve => {
      log.info("Worker got message")
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
class SimpleRepresentationSettings(val matrix:DenseMatrix[Int]) extends RepresentationSettings
class SimpleRepresentation(x:Int = 1001, y:Int = 1001) extends Representation{
  private val matrix = DenseMatrix.zeros[Int](x,y)
  override def workerProps: Props = SimpleRepresentationWorker.props()
  override def insert(p: Pixel): Unit = {
    matrix(p.coordinates._1,p.coordinates._2) = 1
  }

  override def set(representationSettings: RepresentationSettings): Unit = representationSettings match {
    case _ => println("tits")
    //case simpleRepresentationSettings:SimpleRepresentationSettings => matrix = simpleRepresentationSettings.matrix
  }

  def indexGenerator() =
  for (i <- 0 until matrix.rows;
       j<- 0 until matrix.cols) yield (i,j)

  override def iterate( router: Router,sender:ActorRef): Unit = indexGenerator().foreach(p => {
    if(p._1 > 0 && p._1 < matrix.rows){
      if(p._2 > 0  && p._2 < matrix.cols){
        router.route(Convolve(
          matrix( (p._1-1) to (p._1+1), (p._2-1) to (p._2+1) ),
          p._1,
          p._2),
          sender)
      }
    }
  })
}