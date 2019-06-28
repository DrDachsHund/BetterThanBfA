package de.htwg.se.roguelike.model.levelComponent

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Armor, Weapon, _}

trait levelInterface {
  val map:Land[Tile]
  def moveUp(player: EntityInterface): (levelInterface,EntityInterface)
  def moveDown(player: Player): (levelInterface, EntityInterface)
  def moveLeft(player: Player): (levelInterface, EntityInterface)
  def moveRight(player: Player): (levelInterface, EntityInterface)
  def removeElement(col: Int, row: Int, value: Int): levelInterface
}

trait EntityInterface {
  val name: String
  val health: Int
  val maxHealth: Int
  val mana: Int
  val maxMana:Int
  val attack: Int
  val lvl: Int
  val exp: Int
  val posX: Int
  val posY: Int
  val inventory: Inventory
  val helmet: Armor
  val chest: Armor
  val pants: Armor
  val boots: Armor
  val gloves: Armor
  val rightHand: Weapon
  val leftHand: Weapon
  val gulden: Int

  def isAlive: Boolean = health > 0

  def nextEntity(): EntityInterface

  def getArmor: Int = helmet.armor + chest.armor + pants.armor + boots.armor + gloves.armor

  //Template Method
  def getAttack: Double = attack + rightHand.dmg + (leftHand.dmg / 2)
}


