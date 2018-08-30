package gameOfLife.Actors

import akka.actor.{Actor, Props}
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.cluster.pubsub.DistributedPubSub

object TimeActor{
  def apply: TimeActor = new TimeActor()
  def props : Props = Props(new TimeActor)
}
class TimeActor extends Actor{
  val mediator = DistributedPubSub(context.system).mediator
  override def receive:Receive ={
    case t:Trigger => mediator ! Publish("trigger",t)
  }
}



