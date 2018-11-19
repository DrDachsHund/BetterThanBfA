package de.htwg.se.betterthanbfa

import de.htwg.se.betterthanbfa.model._

object BetterThanBfA {
  def main(args: Array[String]): Unit = {
    val player = Player("Ândre Göhring")
    val level = Level(10)
    println(player.toString)
    println("Ich hoffe mal das, das so richtig ist und nicht wieder mit dem sbt rumspackt!!!!!!!!!!!!!!!!!!!!!!!!!")
    val enemy = Enemy(player)
    println(enemy.toString)
    level.initArray
    println(level.toString)

  }
}
