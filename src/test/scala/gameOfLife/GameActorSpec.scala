package gameOfLife

import akka.actor.ActorSystem
import akka.testkit.{ ImplicitSender, TestActors, TestKit }
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import gameOfLife.Actors._

class GameActorSpec() extends TestKit(ActorSystem("MySpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "An GameActor" must {
    "send back messages unchanged" in {
      val echo = system.actorOf(GameActor.props(new SimpleRepresentation(10000, 10000)))
      val pixels = List[(Int, Int)](
        (5, 5), (5, 6), (6, 5), (4, 5))

      pixels.foreach(p => {
        echo ! SetPixel(p)
      })
      //echo ! Trigger

    }

  }
}
