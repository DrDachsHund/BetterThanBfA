package de.htwg.se.roguelike.model

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
  val helmet: Armor
  val chest: Armor
  val pants: Armor
  val boots: Armor
  val gloves: Armor
  val rightHand: Weapon
  val leftHand: Weapon
  val gulden: Int

  def isAlive: Boolean = health > 0

  def getArmor: Int = helmet.armor + chest.armor + pants.armor + boots.armor + gloves.armor

  //Template Method
  def getAttack: Double = attack + rightHand.dmg + (leftHand.dmg / 2)

}
