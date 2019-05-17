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