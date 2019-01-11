package de.htwg.se.betterthanbfa.model.playerComponent

import de.htwg.se.betterthanbfa.model.ItemComponent.{Inventory, Shield, Weapon}

case class Player(playerName: String) {
  val entityN: Int = 1
  val name: String = playerName
  var attack: Int = 10
  var health: Int = 100
  var mana: Int = 100
  var level: Int = 1
  var experience: Int = 0
  var posX: Int = 0
  var posY: Int = 0

  var isBlocking = false

  val inventory: Inventory = new Inventory()

  var shield: Shield = new Shield()
  var weapon: Weapon = new Weapon()

  def isAlive: Boolean = health > 0

  override def toString: String = "Name: " + name +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana +
    "\nExperience: " + experience +
    "\n" + weapon.toString +
    "\n" + shield.toString +
    "\n" + inventory.toString

}