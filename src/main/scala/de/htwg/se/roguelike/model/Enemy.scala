package de.htwg.se.roguelike.model

import scala.util.Random

case class Enemy(name: String = "Skeleton",
                 health: Int = 100,
                 attack: Int = 10,
                 lvl: Int = 0,
                 exp: Int = 20,
                 posX: Int = 0, posY: Int = 0,
                 inventory: Inventory = new Inventory(Vector(),Vector(),Vector()),
                 rightHand: Weapon = Weapon("rightFist"),
                 leftHand: Weapon = Weapon("leftFist"),
                 gulden: Int = 1) extends Entity {


  //Template Method
  override def getAttack: Double = super.getAttack - 2

  def setLoot(): Enemy = {
    val random = Random
    val index = random.nextInt(1 + (lvl / 15))
    var enemy = this
    for (_ <- 0 to index) {
      enemy = enemy.loot()
    }
    enemy
  }

  private def loot(): Enemy = {
    val random = Random
    val itemType = random.nextInt(3) + 1
    itemType match {
      case 1 =>
        val randomWeapon = Weapon("random").getScaledWeapon(lvl) //vll noch while weapon rarity hoch nochmal rollen
        this.copy(rightHand = randomWeapon, inventory = inventory.copy(weapons = inventory.weapons :+ randomWeapon))
      case 2 => this.copy(inventory = inventory.copy(potions = inventory.potions :+ Potion("random")))
      case 3 => this.copy(inventory = inventory.copy(armor = inventory.armor :+ Armor("random"))) //Evemtuel noch sclae hinzufügen
    }
  }

  def setScale(lvl: Int): Enemy = {
    val random = new Random()
    val lvlBuffer = random.nextInt(4) //0-3
    random.nextInt(1) match {
      case 0 => this.copy(lvl = lvl + lvlBuffer, exp = exp * (lvl + lvlBuffer), gulden = gulden * (lvl + lvlBuffer))
      case 1 =>
        if (lvl - lvlBuffer < 1)
          this.copy(lvl = 1, exp = exp * (lvl - lvlBuffer), gulden = gulden * (lvl - lvlBuffer))
        return this.copy(lvl = lvl - lvlBuffer, exp = exp * (lvl - lvlBuffer), gulden = gulden * (lvl - lvlBuffer))
    }
  }


  override def toString: String =
    "Name: " + name +
      "\nhealth: " + health +
      "\nAttack: " + attack +
      "\nExperience: " + exp +
      "\nposX: " + posX +
      "\nposY: " + posY

}
