import akka.actor.{ActorRef, ActorSystem, Props}
import akka.cluster.pubsub.DistributedPubSub
import com.typesafe.config.ConfigFactory
import gameOfLife.Actors._

import scala.reflect.io.File

object main {
  def main(args: Array[String]): Unit = {
    val commonConfig = ConfigFactory.load()
    val system = ActorSystem("cluster")
    println(system.settings)
    val systemMediator = DistributedPubSub(system).mediator

    val timeActor:ActorRef = system.actorOf(TimeActor.props)
    val socketActor:ActorRef = system.actorOf(SocketActor.props)
    val gameActor = system.actorOf(GameActor.props(new SimpleRepresentation()))

    val sleepTime = 10
    val triggerDiff = 100
    var timeSinceTrigger = 0

    for (i <- 1 to 1000) {
      Thread.sleep(sleepTime)
      socketActor ! SetPixel(Pixel.randomTuple(-1000,1000,-1000,1000))
      if (timeSinceTrigger > triggerDiff){
        timeActor ! Trigger()
        timeSinceTrigger = 0
      }
      else{
        timeSinceTrigger += sleepTime
      }
    }
    system.terminate()
}

}
