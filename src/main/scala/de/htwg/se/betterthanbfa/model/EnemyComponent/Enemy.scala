package de.htwg.se.betterthanbfa.model.EnemyComponent

import de.htwg.se.betterthanbfa.model.ItemComponent.{Shield, Weapon}
import de.htwg.se.betterthanbfa.model.playerComponent.Player

case class Enemy(player: Player, posX: Int , posY: Int) {
  val r = new scala.util.Random

  val entityN: Int = 2
  var attack: Int = 10
  var health: Int = 100
  var maxHealth: Int = 100
  var mana: Int = 100
  //var posX: Int = x glaube unnötig weil in übergabe parameter
  //var posY: Int = y
  var level: Int = setLevel()
  var name = Array("Zombie", "Skeleton", "Human", "Bandit", "Skeleton-Mage")
  var typ: String = setType()

  var experience = 100

  var isBlocking = false

  var shield: Shield = new Shield()
  shield.randomShield(player)
  var weapon: Weapon = new Weapon()
  weapon.randomWeapon(player)

  //private
  private def setType() = name(r.nextInt(5))

  private def setLevel() = {
    var tmp = player.level + (3 - r.nextInt(7))
    if (tmp < 1) tmp = player.level
    experience = tmp * 10
    tmp
  }

  //public
  def isAlive: Boolean = health > 0

  override def toString: String = "Typ: " + typ +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana
}
