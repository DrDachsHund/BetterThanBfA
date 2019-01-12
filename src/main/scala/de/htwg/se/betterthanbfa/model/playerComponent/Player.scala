package de.htwg.se.betterthanbfa.model.playerComponent

import de.htwg.se.betterthanbfa.model.ItemComponent.{Inventory, Shield, Weapon}

case class Player(playerName: String) {
  val entityN: Int = 1
  val name: String = playerName
  var attack: Int = 10
  var health: Int = 100
  var maxHealth: Int = 100
  var mana: Int = 100
  var maxMana: Int = 100
  var level: Int = 1
  var experience: Int = 0
  var maxExperience: Int = 100
  var posX: Int = 0
  var posY: Int = 0

  var isBlocking = false

  val inventory: Inventory = new Inventory()

  var shield: Shield = new Shield()
  var weapon: Weapon = new Weapon()

  def isAlive: Boolean = health > 0

  def heal(heal: Int): Unit = {
    health += heal
    if (health > maxHealth) {
      health = maxHealth
    }
  }
  def refresh(power: Int): Unit = {
    mana += power
    if (mana > maxMana) {
      mana = maxMana
    }
  }

  def collectExperience(experience: Int): Boolean = {
    this.experience += experience
    if (this.experience > maxExperience) {
      level += 1
      this.experience -= maxExperience
      maxExperience = maxExperience * level
      maxHealth += 10
      maxMana += 5
      health = maxHealth
      mana = maxMana
      return true
    }
    return false
  }

  def equipItem(): Unit = {


  }

  override def toString: String = "Name: " + name +
    "\nLevel: " + level +
    "\nHealth: " + health +
    "\nMana: " + mana +
    "\nExperience: " + experience +
    "\n" + weapon.toString +
    "\n" + shield.toString +
    "\n" + inventory.toString

}