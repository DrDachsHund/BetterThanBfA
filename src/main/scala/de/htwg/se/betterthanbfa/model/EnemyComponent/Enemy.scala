package de.htwg.se.betterthanbfa.model.EnemyComponent

import de.htwg.se.betterthanbfa.model.playerComponent.Player

case class Enemy(player: Player, x: Int , y: Int) {
  val r = new scala.util.Random

  val entityN: Int = 2
  var health: Int = 100
  var mana: Int = 100
  var posX: Int = x
  var posY: Int = y
  var level: Int = setLevel()
  var name = Array("Zombie", "Skeleton", "Human", "Bandit", "Skeleton-Mage")
  var typ: String = setType()

  //private
  private def setType() = name(r.nextInt(5))

  private def setLevel() = {
    var tmp = player.level + (3 - r.nextInt(7))
    if (tmp < 1) tmp = player.level
    tmp
  }

  //public
  def isAlive: Boolean = health > 0

  override def toString: String = "Typ: " + typ +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana
}
