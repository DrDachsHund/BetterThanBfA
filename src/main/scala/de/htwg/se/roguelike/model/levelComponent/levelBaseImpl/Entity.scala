package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.{ArmorInterface, WeaponInterface}

trait Entity {
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
  val helmet: ArmorInterface
  val chest: ArmorInterface
  val pants: ArmorInterface
  val boots: ArmorInterface
  val gloves: ArmorInterface
  val rightHand: WeaponInterface
  val leftHand: WeaponInterface
  val gulden: Int

  def isAlive: Boolean = health > 0

  def getArmor: Int = helmet.armor + chest.armor + pants.armor + boots.armor + gloves.armor

  //Template Method
  def getAttack: Double = attack + rightHand.dmg + (leftHand.dmg / 2)

}
