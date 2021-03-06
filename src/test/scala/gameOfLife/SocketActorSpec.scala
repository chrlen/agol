package gameOfLife

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestActors, TestKit }
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import gameOfLife.Actors._

class SocketActorSpec() extends TestKit(ActorSystem("MySpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "An SocketActor" must {
    "send back messages unchanged" in {
      val echo = system.actorOf(GameActor.props(new SimpleRepresentation(100,100)))
      //echo ! "hello world"
      //expectMsg("hello world")
    }
  }
}
