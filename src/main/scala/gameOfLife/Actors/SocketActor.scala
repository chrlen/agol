package gameOfLife.Actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Publish, Subscribe}

object SocketActor{
  def apply: SocketActor = new SocketActor()
  def props = Props(new SocketActor)
}
class SocketActor extends Actor with ActorLogging{
  private val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe("game",self)

  log.info("MainActor => online")
  override def receive = {
    case s:SetPixel => mediator ! Publish("commands",s)
    case r:Result => log.info(r.toString)
    case _ => log.info("Unknown Message")
  }
}
