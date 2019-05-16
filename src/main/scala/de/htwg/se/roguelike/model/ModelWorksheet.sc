import scala.util.Random

object Context2 {

  var strategy = if (Random.nextInt() % 2 == 0) strategy1 else strategy2

  def strategy1 = println("I am strategy 1")

  def strategy2 = println("I am strategy 2")
}

Context2.strategy
Context2.strategy
Context2.strategy
Context2.strategy
Context2.strategy
Context2.strategy
Context2.strategy
Context2.strategy


object Context1 {
  trait Strategy {
    def execute
  }
  class Strategy1 extends Strategy {
    override def execute = println("I am strategy 1")
  }
  class Strategy2 extends Strategy {
    override def execute = println("I am strategy 2")
  }
  var strategy = if (Random.nextInt() % 2 == 0) new Strategy1 else new Strategy2
}

Context1.strategy.execute
Context1.strategy.execute
Context1.strategy.execute
Context1.strategy.execute
Context1.strategy.execute
Context1.strategy.execute

trait Event

case class OnEvent() extends Event

case class OffEvent() extends Event


object StateContext2 {
  var state = onState
  def handle(e: Event) = {
    e match {
      case on: OnEvent => state = onState
      case off: OffEvent => state = offState
    }
    state
  }

  def onState = println("I am on")
  def offState = println("I am off")
}


StateContext2.handle(OnEvent())
StateContext2.handle(OffEvent())
