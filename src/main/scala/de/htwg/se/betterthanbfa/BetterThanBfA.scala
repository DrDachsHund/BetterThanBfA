package de.htwg.se.betterthanbfa

import de.htwg.se.betterthanbfa.model._

object BetterThanBfA {
  def main(args: Array[String]): Unit = {
    val player = Player("Ândre Göhring")
    val level = Level(10)
    println(player.toString)
    println("Ich hoffe mal das, das so richtig ist und nicht wieder mit dem sbt rumspackt!!!!!!!!!!!!!!!!!!!!!!!!!")
    player.level = 10
    val enemy = Enemy(player)
    val enemy2 = Enemy(player)
    val enemy3 = Enemy(player)
    println(enemy.toString)
    println(enemy2.toString)
    println(enemy3.toString)

    level.update(player.posY,player.posX,1)
    player.posY = 7
    player.posX = 6
    level.update(player.posY,player.posX,1)
    player.moveLeft()
    level.update(player.posY,player.posX,1)
    println(level.toString)

    val testfight = new Fight
    testfight.fight(player,enemy2)

  }
}
