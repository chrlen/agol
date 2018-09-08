package gameOfLife.Actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

object GameActor {
  def apply(representation: Representation): GameActor = new GameActor(representation)
  def props(representation: Representation):Props = Props(GameActor(representation))
}
class GameActor(val representation: Representation,workers:Int = 4) extends Actor with ActorLogging{
  private val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe("commands",self)
  mediator ! Subscribe("trigger",self)

  var router = {
    val routees = Vector.fill(workers) {
      val r = context.actorOf(representation.workerProps)
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }
  override def receive: Receive = {
    case s:SetPixel => representation.insert(s)
    case Trigger() => representation.iterate(router,self)
    case r:RepresentationSettings => representation.set(r)
    case _ => log.info("unknown")
  }
}
