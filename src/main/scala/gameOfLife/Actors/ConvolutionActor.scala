package gameOfLife.Actors

import akka.actor.{Actor, ActorLogging, Props}
import breeze.linalg.sum
import breeze.math._
import breeze.numerics._

object ConvolutionActor {
  def props(): Props = Props(new ConvolutionActor)

  def apply: ConvolutionActor = new ConvolutionActor()
}

class ConvolutionActor extends Actor with ActorLogging {
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

    case _ => log.info("ConvActor")
  }
}
